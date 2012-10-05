package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class StyleTestController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def test() {
		def test
		def params = [:]
		for (def i = 1; i <= 50; i++) {
			params.name = 'StyleTest ' + i
			params.column1 = (i * 5).toString()
			params.column2 = ((i - 2) * 3 / 2).toString()
			params.column3 = (i + i).toString()
			//test = new StyleTest(params)
			//println test.save(flush: true)
			println params
			println i
		}
	}

	def list(Integer max) {
		def listReturn = [:]
		params.max = Math.min(max ?: 10, 100)
		listReturn.list = StyleTest.list(params)
		listReturn.count = StyleTest.count()

		def model = [list: listReturn]

		withFormat {
			html { model }
			json { render model as JSON }
		}
	}

	def create() {
		[styleTestInstance: new StyleTest(params)]
	}

	def save() {
		def styleTestInstance = new StyleTest(params)
		if (!styleTestInstance.save(flush: true)) {
			render(view: "create", model: [styleTestInstance: styleTestInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), styleTestInstance.id])
		redirect(action: "show", id: styleTestInstance.id)
	}

	def show(Long id) {
		def styleTestInstance = StyleTest.get(id)
		if (!styleTestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), id])
			redirect(action: "list")
			return
		}

		[styleTestInstance: styleTestInstance]
	}

	def edit(Long id) {
		def styleTestInstance = StyleTest.get(id)
		if (!styleTestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), id])
			redirect(action: "list")
			return
		}

		[styleTestInstance: styleTestInstance]
	}

	def update(Long id, Long version) {
		def styleTestInstance = StyleTest.get(id)
		if (!styleTestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (styleTestInstance.version > version) {
				styleTestInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'styleTest.label', default: 'StyleTest')] as Object[],
						  "Another user has updated this StyleTest while you were editing")
				render(view: "edit", model: [styleTestInstance: styleTestInstance])
				return
			}
		}

		styleTestInstance.properties = params

		if (!styleTestInstance.save(flush: true)) {
			render(view: "edit", model: [styleTestInstance: styleTestInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), styleTestInstance.id])
		redirect(action: "show", id: styleTestInstance.id)
	}

	def delete(Long id) {
		def styleTestInstance = StyleTest.get(id)
		if (!styleTestInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), id])
			redirect(action: "list")
			return
		}

		try {
			styleTestInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'styleTest.label', default: 'StyleTest'), id])
			redirect(action: "show", id: id)
		}
	}
}
