package com.centurylink.hospital

class SecurityController {

	def springSecurityService

	def index() {
		render ''
	}

	def info() {
		def securityInfo = [loggedIn: springSecurityService.isLoggedIn(), username: '']

		if (securityInfo.loggedIn) {
			securityInfo.username = springSecurityService.principal.username
		}

		response.contentType = 'text/javascript'

		render 'var securityInfo = ' + securityInfo.encodeAsJSON() + ';'
	}
}
