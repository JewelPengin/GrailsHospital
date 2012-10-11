package com.centurylink.hospital

import grails.converters.JSON
import org.apache.commons.lang3.text.WordUtils

class AjaxController {

    def springSecurityService

    def index(String type, String artifact, String function) {
        def response = [
            error: false
            , message: ''
            , data: ''
        ]
        def grailsClass = grailsApplication.getArtefactByLogicalPropertyName(WordUtils.capitalize(type), artifact)
        def method
        def instance

        /*println grailsClass.getFullName()
        println grailsClass.getLogicalPropertyName()
        println grailsClass.getName()
        println grailsClass.getPropertyName()
        println grailsClass.getShortName()*/

        if (grailsClass == null) {
            response.error = true
            response.message = 'invalid request - invalid artifact/type combination'
            render response as JSON
            return
        }

        def clazz = grailsClass.getClazz()
        // TODO: figure out how to get the actual instance that grails has already.
        instance = clazz.newInstance()
        // this code snippet allows any instance to be DI'ed
        applicationContext.beanFactory.autowireBeanProperties(instance, applicationContext.beanFactory.AUTOWIRE_BY_NAME, false)

        // This was an attempt to get the actual instance... seems like it didn't work though... :(
        // Since beans are stored two different ways, we need to try both.
        /*try {
            instance = applicationContext.beanFactory.getBean(grailsClass.getPropertyName())
        } catch (java.lang.Exception ex) {
            try {
                instance = applicationContext.beanFactory.getBean(grailsClass.getFullName())
            } catch (java.lang.Exception ex2) {
                response.error = true
                response.message = 'invalid request - cannot find bean'
                render response as JSON
                return
            }
        }*/

        def methods = clazz.getMethods()
        method = methods.find{ m -> m.name == function }
        // TODO: support for overloaded methods

        // this was the original way, but the problem was we needed to know the exact number of paramaters.
        /*try {
            method = clazz.getMethod(function, Object)
        } catch (java.lang.NoSuchMethodException ex) {
            response.error = true
            response.message = 'invalid request - incorrect function'
            render response as JSON
            return
        }*/

        // put the args into an array to allow functions with more than one parameter
        def args = params['_[]']
        /* this was an older way... but thinking of going with the above because it maintains order
        params.each { k, v ->
            if (k.charAt(0) == '_') {
                args.add(v)
            }
        }
        args = args.toArray()
        */

        // TODO: !SECURITY! make sure security is handled, because it currently isn't.

        try {
            response.data = method.invoke(instance, args)
                // the toArray makes it an object array with allows us to pass each element as an argument
        } catch (java.lang.Exception ex) {
            response.error = true
            response.message = ex.cause != null ? ex.cause.toString() : ex.message
        }

        render response as JSON
    }

}
