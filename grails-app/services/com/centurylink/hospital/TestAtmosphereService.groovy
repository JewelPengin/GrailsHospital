package com.centurylink.hospital

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
        }
        else if (event.message) {
            println "onStateChange, message: ${event.message}"

            event.resource.response.writer.with {
                write event.message
                flush()
            }
        }
    }
}
