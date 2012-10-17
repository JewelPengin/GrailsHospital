package com.centurylink.hospital

import com.odelia.grails.plugins.atmosphere.StratosphereServlet
import com.centurylink.hospital.Notification

class NotificationService {

	def applicationContextHolder
	def securityService

	def send(String message
			, String type
			, String link
			, String person = 'all'
	) {
		if (person == 'current') {
			person = securityService.getCurrentUsername()
			if (person == null) {
				// silently fail?
				return
				//throw new Exception('No current user logged in - ')
			}
		}

		// TODO: implement caching on all of this!
		def broadcaster = [:]
		def servletContext = applicationContextHolder.getServletContext()
		servletContext[StratosphereServlet.ATMOSPHERE_PLUGIN_HANDLERS_CONFIG].each {
                broadcaster."${it.key}" = it.value.broadcaster
        }
		def personObj = person == 'all' ? null : securityService.getPersonFromUsername(person)

		new Notification(message: message
						, type: type
						, link: link
						, person: personObj
		).save(flush: true, failOnError: true)

		broadcaster['/atmosphere/notification/' + person].broadcast(
			[message: message, type: type, link: link]
		)

		return
	}

	def getForCurrentUser() {
		def username = securityService.getCurrentUsername()

		// This seemed the only way to get a null or valid relation hql
		return Notification.findAll("from Notification as n \
			left join n.person as p \
			where n.person is null \
			or p.username = '${username}' \
			order by n.dateCreated desc", [max: 10])
	}
}
