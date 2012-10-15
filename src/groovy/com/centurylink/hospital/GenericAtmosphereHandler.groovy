package com.centurylink.hospital

import grails.converters.JSON
import org.atmosphere.cpr.AtmosphereHandler
import org.atmosphere.cpr.AtmosphereResource
import org.atmosphere.cpr.AtmosphereResourceEvent
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class GenericAtmosphereHandler implements AtmosphereHandler <HttpServletRequest, HttpServletResponse> {

    void onRequest(AtmosphereResource<HttpServletRequest, HttpServletResponse> event) throws IOException {
    	//println "onRequest, $event"

        // Mark this connection as suspended.
        event.suspend()
    }

    void onStateChange(AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event) throws IOException {
    	if (event.cancelled){
            //println "onStateChange, cancelling $event"
        } else if (event.message) {
            //println "onStateChange, message: ${event.message}"

            event.resource.response.writer.with {
                def msg = event.message
                // for whatever reason, even though it knows it's a linked hash map class, the class getter returns null...
                if (msg.class?.name != 'java.lang.String') {
                    msg = event.message.encodeAsJSON().toString()
                } else {
                    msg = '"' + msg.encodeAsJavaScript() + '"'
                }
                write msg
                flush()
            }
        }
    }

    void destroy() {}

}