package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured (['ROLE_ADMIN'])

class PrescriptionController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[prescriptionInstanceList: Prescription.list(params), prescriptionInstanceTotal: Prescription.count()]
	}

	def create() {
		[prescriptionInstance: new Prescription(params)]
	}

	def save() {
		def prescriptionInstance = new Prescription(params)
		if (!prescriptionInstance.save(flush: true)) {
			render(view: "create", model: [prescriptionInstance: prescriptionInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'prescription.label', default: 'Prescription'), prescriptionInstance.id])
		redirect(action: "show", id: prescriptionInstance.id)
	}

	def show(Long id) {
		def prescriptionInstance = Prescription.get(id)
		if (!prescriptionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'prescription.label', default: 'Prescription'), id])
			redirect(action: "list")
			return
		}

		[prescriptionInstance: prescriptionInstance]
	}

	def edit(Long id) {
		def prescriptionInstance = Prescription.get(id)
		if (!prescriptionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'prescription.label', default: 'Prescription'), id])
			redirect(action: "list")
			return
		}

		[prescriptionInstance: prescriptionInstance]
	}

	def update(Long id, Long version) {
		def prescriptionInstance = Prescription.get(id)
		if (!prescriptionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'prescription.label', default: 'Prescription'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (prescriptionInstance.version > version) {
				prescriptionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'prescription.label', default: 'Prescription')] as Object[],
						  "Another user has updated this Prescription while you were editing")
				render(view: "edit", model: [prescriptionInstance: prescriptionInstance])
				return
			}
		}

		prescriptionInstance.properties = params

		if (!prescriptionInstance.save(flush: true)) {
			render(view: "edit", model: [prescriptionInstance: prescriptionInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'prescription.label', default: 'Prescription'), prescriptionInstance.id])
		redirect(action: "show", id: prescriptionInstance.id)
	}

	def delete(Long id) {
		def prescriptionInstance = Prescription.get(id)
		if (!prescriptionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'prescription.label', default: 'Prescription'), id])
			redirect(action: "list")
			return
		}

		try {
			prescriptionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'prescription.label', default: 'Prescription'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'prescription.label', default: 'Prescription'), id])
			redirect(action: "show", id: id)
		}
	}
}
