package hospital



import org.junit.*
import grails.test.mixin.*

@TestFor(StyleTestController)
@Mock(StyleTest)
class StyleTestControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/styleTest/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.styleTestInstanceList.size() == 0
        assert model.styleTestInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.styleTestInstance != null
    }

    void testSave() {
        controller.save()

        assert model.styleTestInstance != null
        assert view == '/styleTest/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/styleTest/show/1'
        assert controller.flash.message != null
        assert StyleTest.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/styleTest/list'

        populateValidParams(params)
        def styleTest = new StyleTest(params)

        assert styleTest.save() != null

        params.id = styleTest.id

        def model = controller.show()

        assert model.styleTestInstance == styleTest
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/styleTest/list'

        populateValidParams(params)
        def styleTest = new StyleTest(params)

        assert styleTest.save() != null

        params.id = styleTest.id

        def model = controller.edit()

        assert model.styleTestInstance == styleTest
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/styleTest/list'

        response.reset()

        populateValidParams(params)
        def styleTest = new StyleTest(params)

        assert styleTest.save() != null

        // test invalid parameters in update
        params.id = styleTest.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/styleTest/edit"
        assert model.styleTestInstance != null

        styleTest.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/styleTest/show/$styleTest.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        styleTest.clearErrors()

        populateValidParams(params)
        params.id = styleTest.id
        params.version = -1
        controller.update()

        assert view == "/styleTest/edit"
        assert model.styleTestInstance != null
        assert model.styleTestInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/styleTest/list'

        response.reset()

        populateValidParams(params)
        def styleTest = new StyleTest(params)

        assert styleTest.save() != null
        assert StyleTest.count() == 1

        params.id = styleTest.id

        controller.delete()

        assert StyleTest.count() == 0
        assert StyleTest.get(styleTest.id) == null
        assert response.redirectedUrl == '/styleTest/list'
    }
}
