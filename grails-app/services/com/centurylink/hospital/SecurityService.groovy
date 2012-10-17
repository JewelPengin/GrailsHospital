package com.centurylink.hospital

import com.centurylink.hospital.auth.Person

class SecurityService {

	def springSecurityService

	def getPersonFromUsername(String username) {
		return Person.findByUsername(username)
	}

	def getCurrentUsername() {
		if (springSecurityService.principal.class.name == 'java.lang.String') {
			return null
		}

		return springSecurityService.principal?.username
	}

	def getCurrentPerson() {
		def username = getCurrentUsername()
		if (username != null) {
			return getPersonFromUsername(username)
		}
		return null
	}
}
