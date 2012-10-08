package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured (['ROLE_USER', 'ROLE_ADMIN'])

class PatientController {

	def scaffold = true

}
