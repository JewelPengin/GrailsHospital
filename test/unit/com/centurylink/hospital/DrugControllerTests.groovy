package com.centurylink.hospital



import org.junit.*
import grails.test.mixin.*

@TestFor(DrugController)
@Mock(Drug)
class DrugControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/drug/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.drugInstanceList.size() == 0
        assert model.drugInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.drugInstance != null
    }

    void testSave() {
        controller.save()

        assert model.drugInstance != null
        assert view == '/drug/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/drug/show/1'
        assert controller.flash.message != null
        assert Drug.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/drug/list'

        populateValidParams(params)
        def drug = new Drug(params)

        assert drug.save() != null

        params.id = drug.id

        def model = controller.show()

        assert model.drugInstance == drug
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/drug/list'

        populateValidParams(params)
        def drug = new Drug(params)

        assert drug.save() != null

        params.id = drug.id

        def model = controller.edit()

        assert model.drugInstance == drug
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/drug/list'

        response.reset()

        populateValidParams(params)
        def drug = new Drug(params)

        assert drug.save() != null

        // test invalid parameters in update
        params.id = drug.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/drug/edit"
        assert model.drugInstance != null

        drug.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/drug/show/$drug.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        drug.clearErrors()

        populateValidParams(params)
        params.id = drug.id
        params.version = -1
        controller.update()

        assert view == "/drug/edit"
        assert model.drugInstance != null
        assert model.drugInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/drug/list'

        response.reset()

        populateValidParams(params)
        def drug = new Drug(params)

        assert drug.save() != null
        assert Drug.count() == 1

        params.id = drug.id

        controller.delete()

        assert Drug.count() == 0
        assert Drug.get(drug.id) == null
        assert response.redirectedUrl == '/drug/list'
    }
}
