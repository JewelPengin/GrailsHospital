atmospherePlugin {
	servlet {
		initParams = [
			'org.atmosphere.cpr.sessionSupport': 'false',
			'org.atmosphere.cpr.cometSupport': 'org.atmosphere.container.Tomcat7CometSupport',
		]
		urlPattern = '/atmosphere/*'
	}
	handlers {
		// This closure is used to generate the atmosphere.xml using a MarkupBuilder instance in META-INF folder
		atmosphereDotXml = {
			'atmosphere-handler'('context-root': '/atmosphere/notification/all', 'class-name': 'com.centurylink.hospital.GenericAtmosphereHandler')
		}
	}
}