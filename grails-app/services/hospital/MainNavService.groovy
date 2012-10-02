package hospital

import org.codehaus.groovy.grails.commons.GrailsApplication

class MainNavService {

    def grailsApplication

    def get() {
        def links = [
            [code: 'home', name: "Home", title: "Home", url: "/", active: true, children: []]
        ]

        def controllers = grailsApplication.controllerClasses.sort { it.fullName }
        controllers.eachWithIndex {item, idx ->
            links.add([code: "name", name: item.name, title: item.fullName, url: item.logicalPropertyName, active: true, children: []])
        }


        return links
    }

    def getCurrentPageNav(request, nav = get()) {
        nav.each {item ->
            if (item.url == request.request.getRequestURI()) {
                return item
            }
            def childNav = getCurrentPageNav(request, item.children)
            if (childNav != null) {
                return childNav
            }
        }
        return null
    }
}
