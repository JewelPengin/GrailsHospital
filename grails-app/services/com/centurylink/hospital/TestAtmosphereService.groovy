package com.centurylink.hospital

import grails.converters.JSON

class TestAtmosphereService {

    static transactional = false

    static atmosphere = [mapping: '/atmosphere/sample']

    def onRequest = { event ->
        println "onRequest, $event"

        // Mark this connection as suspended.
        event.suspend()
    }

    def onStateChange = { event ->
        if (event.cancelled){
            println "onStateChange, cancelling $event"
        } else if (event.message) {
            println "onStateChange, message: ${event.message}"

            event.resource.response.writer.with {
                println event.message
                println event.message.class
                def msg = event.message
                // for whatever reason, even though it knows it's a linked hash map class, the class getter returns null...
                if (msg.class?.name != 'java.lang.String') {
                    msg = event.message.encodeAsJSON().toString()
                } else {
                    msg = msg.encodeAsJavaScript()
                }
                write msg
                flush()
            }
        }
    }
}
