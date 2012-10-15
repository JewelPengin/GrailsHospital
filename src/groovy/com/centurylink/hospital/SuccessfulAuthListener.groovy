package com.centurylink.hospital

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.atmosphere.cpr.BroadcasterFactory

class SuccessfulAuthListener implements ApplicationListener<AuthenticationSuccessEvent> {

	def contextHolder

	void onApplicationEvent(AuthenticationSuccessEvent event) {
		def loggingInUsername = event.authentication.principal.username
		def servletContext = contextHolder.getServletContext()

		def servlet = servletContext[com.odelia.grails.plugins.atmosphere.StratosphereServlet.ATMOSPHERE_PLUGIN_ATMOSPHERE_SERVLET]

		// TODO: lookup handlers

		try {
			servlet.addAtmosphereHandler('/atmosphere/notification/' + loggingInUsername, contextHolder.getBean('genericAtmosphereHandler'))
		} catch (java.lang.Exception ex) { /* intentionally do nothing... */ }
	}

}