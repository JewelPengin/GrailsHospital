package com.centurylink.hospital

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent

class SuccessfulAuthListener implements ApplicationListener<AuthenticationSuccessEvent> {

	def contextHolder

	void onApplicationEvent(AuthenticationSuccessEvent event) {
		def loggingInUsername = event.authentication.principal.username
		def servletContext = contextHolder.getServletContext()

		def servlet = servletContext[com.odelia.grails.plugins.atmosphere.StratosphereServlet.ATMOSPHERE_PLUGIN_ATMOSPHERE_SERVLET]

		// TODO: Instead of swallowing the exception, we should actually do if(handlerExists)... but need to figure out how to do that.
		try {
			servlet.addAtmosphereHandler('/atmosphere/notification/' + loggingInUsername, contextHolder.getBean('genericAtmosphereHandler'))
		} catch (java.lang.Exception ex) { /* intentionally do nothing... */ }
	}

}