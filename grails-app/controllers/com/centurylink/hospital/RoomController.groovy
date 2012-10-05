package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured (['ROLE_USER', 'ROLE_ADMIN'])

class RoomController {

	def scaffold = true

}
