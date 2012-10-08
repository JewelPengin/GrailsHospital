package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured (['ROLE_ADMIN'])

class DrugController {

	def scaffold = true

}
