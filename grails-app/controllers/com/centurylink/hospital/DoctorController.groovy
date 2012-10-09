package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured (['ROLE_USER', 'ROLE_ADMIN'])

class DoctorController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {

        //JMS notify expired prescriptions
        NotificationController nControl =  new NotificationController()
        nControl.sendJMSMessageCall()

        def listReturn = [:]
		params.max = Math.min(max ?: 20, 100)
		listReturn.list = Doctor.list(params)
		listReturn.count = Doctor.count()

		def model = [list: listReturn]

		withFormat {
			html { model }
			json { render model as JSON }
		}
	}

	def create() {
		[doctorInstance: new Doctor(params)]
	}

	def save() {
		def doctorInstance = new Doctor(params)
		if (!doctorInstance.save(flush: true)) {
			render(view: "create", model: [doctorInstance: doctorInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'doctor.label', default: 'Doctor'), doctorInstance.id])
		redirect(action: "show", id: doctorInstance.id)
	}

	def show(Long id) {
		def doctorInstance = Doctor.get(id)
		if (!doctorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'doctor.label', default: 'Doctor'), id])
			redirect(action: "list")
			return
		}

		[doctorInstance: doctorInstance]
	}

	def edit(Long id) {
		def doctorInstance = Doctor.get(id)
		if (!doctorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'doctor.label', default: 'Doctor'), id])
			redirect(action: "list")
			return
		}

		[doctorInstance: doctorInstance]
	}

	def update(Long id, Long version) {
		def doctorInstance = Doctor.get(id)
		if (!doctorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'doctor.label', default: 'Doctor'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (doctorInstance.version > version) {
				doctorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'doctor.label', default: 'Doctor')] as Object[],
						"Another user has updated this Doctor while you were editing")
				render(view: "edit", model: [doctorInstance: doctorInstance])
				return
			}
		}

		doctorInstance.properties = params

		if (!doctorInstance.save(flush: true)) {
			render(view: "edit", model: [doctorInstance: doctorInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'doctor.label', default: 'Doctor'), doctorInstance.id])
		redirect(action: "show", id: doctorInstance.id)
	}

	def delete(Long id) {
		def doctorInstance = Doctor.get(id)
		if (!doctorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'doctor.label', default: 'Doctor'), id])
			redirect(action: "list")
			return
		}

	}
}