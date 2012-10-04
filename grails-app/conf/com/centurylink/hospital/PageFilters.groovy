package com.centurylink.hospital

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class PageFilters {

    def navService
    def springSecurityService

    def filters = {
        all(controller:'*', action:'*') {
            before = {

                if (!springSecurityService.isLoggedIn()
                    && controllerName != 'login') {
                    redirect controller: 'login', action: 'auth'
                }

                /* very experimental code for getting the columns of a linked domain class - JWL
                if (controllerName != null) {
                    def controllerChars = controllerName.toCharArray()
                    controllerChars[0] = controllerChars[0].toUpperCase()
                    def controllerDomainClass = grailsApplication.getDomainClass('hospital.' + controllerChars)
                    if (controllerDomainClass != null) {
                        def domainColumns = controllerDomainClass.properties.collect { if (it.persistent) { it.name } }
                    }
                }
                */
            }
            after = { Map model ->
                if (model != null) {
                    model.mainNav = navService.get()
                    model.pageNav = navService.getCurrentPageNav(request)
                    model.customTags = [group: 0, depth: -1, tags: []]
                }
            }
        }
    }
}
