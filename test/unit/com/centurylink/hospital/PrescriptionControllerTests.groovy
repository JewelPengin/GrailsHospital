package com.centurylink.hospital



import org.junit.*
import grails.test.mixin.*

@TestFor(PrescriptionController)
@Mock(Prescription)
class PrescriptionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/prescription/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.prescriptionInstanceList.size() == 0
        assert model.prescriptionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.prescriptionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.prescriptionInstance != null
        assert view == '/prescription/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/prescription/show/1'
        assert controller.flash.message != null
        assert Prescription.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/prescription/list'

        populateValidParams(params)
        def prescription = new Prescription(params)

        assert prescription.save() != null

        params.id = prescription.id

        def model = controller.show()

        assert model.prescriptionInstance == prescription
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/prescription/list'

        populateValidParams(params)
        def prescription = new Prescription(params)

        assert prescription.save() != null

        params.id = prescription.id

        def model = controller.edit()

        assert model.prescriptionInstance == prescription
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/prescription/list'

        response.reset()

        populateValidParams(params)
        def prescription = new Prescription(params)

        assert prescription.save() != null

        // test invalid parameters in update
        params.id = prescription.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/prescription/edit"
        assert model.prescriptionInstance != null

        prescription.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/prescription/show/$prescription.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        prescription.clearErrors()

        populateValidParams(params)
        params.id = prescription.id
        params.version = -1
        controller.update()

        assert view == "/prescription/edit"
        assert model.prescriptionInstance != null
        assert model.prescriptionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/prescription/list'

        response.reset()

        populateValidParams(params)
        def prescription = new Prescription(params)

        assert prescription.save() != null
        assert Prescription.count() == 1

        params.id = prescription.id

        controller.delete()

        assert Prescription.count() == 0
        assert Prescription.get(prescription.id) == null
        assert response.redirectedUrl == '/prescription/list'
    }
}
