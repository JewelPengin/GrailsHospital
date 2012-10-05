package com.centurylink.hospital

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured (['ROLE_ADMIN'])

class DrugController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [drugInstanceList: Drug.list(params), drugInstanceTotal: Drug.count()]
    }

    def create() {
        [drugInstance: new Drug(params)]
    }

    def save() {
        def drugInstance = new Drug(params)
        if (!drugInstance.save(flush: true)) {
            render(view: "create", model: [drugInstance: drugInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'drug.label', default: 'Drug'), drugInstance.id])
        redirect(action: "show", id: drugInstance.id)
    }

    def show(Long id) {
        def drugInstance = Drug.get(id)
        if (!drugInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'drug.label', default: 'Drug'), id])
            redirect(action: "list")
            return
        }

        [drugInstance: drugInstance]
    }

    def edit(Long id) {
        def drugInstance = Drug.get(id)
        if (!drugInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'drug.label', default: 'Drug'), id])
            redirect(action: "list")
            return
        }

        [drugInstance: drugInstance]
    }

    def update(Long id, Long version) {
        def drugInstance = Drug.get(id)
        if (!drugInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'drug.label', default: 'Drug'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (drugInstance.version > version) {
                drugInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'drug.label', default: 'Drug')] as Object[],
                        "Another user has updated this Drug while you were editing")
                render(view: "edit", model: [drugInstance: drugInstance])
                return
            }
        }

        drugInstance.properties = params

        if (!drugInstance.save(flush: true)) {
            render(view: "edit", model: [drugInstance: drugInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'drug.label', default: 'Drug'), drugInstance.id])
        redirect(action: "show", id: drugInstance.id)
    }

    def delete(Long id) {
        def drugInstance = Drug.get(id)
        if (!drugInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'drug.label', default: 'Drug'), id])
            redirect(action: "list")
            return
        }

        try {
            drugInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'drug.label', default: 'Drug'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'drug.label', default: 'Drug'), id])
            redirect(action: "show", id: id)
        }
    }
}
