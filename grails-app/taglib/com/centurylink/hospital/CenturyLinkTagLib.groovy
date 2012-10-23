package com.centurylink.hospital

import grails.converters.JSON

class CenturyLinkTagLib {
	static namespace = 'cl'

	def grid = { attrs, body ->
		helpers.startTag(tag: 'grid')

		// param attributes
		if (!attrs.containsKey('name')) {
			throwTagError('The grid tag must have a name attribute')
		}
		if (!attrs.containsKey('data')) {
			throwTagError('The grid tag must have a data attribute')
		}
		if (!attrs.containsKey('id')) {
			attrs.id = attrs.name.replaceAll(/\s+/, '-')
		}
		if (!attrs.containsKey('rows')) {
			attrs.rows = -1
		} else {
			attrs.rows = attrs.rows.toInteger()
		}

		def localData = [attrs: attrs]
		helpers.updateTag(data: localData)

		// run what we have inside our grid, but don't output (no need)
		body()

		def columnData = helpers.getChildTags().collect { if (it.tag == 'column') { it.data.attrs } }
		def dataList = localData.attrs.data.list

		if (localData.attrs.rows >= 0 && dataList.size() >= localData.attrs.rows) {
			dataList = dataList.subList(0, localData.attrs.rows)
		}

		out << r.script() {
			out << """\
				\$(function(){
					\$('#${ localData.attrs.id }').grid({
						data: ${ dataList.encodeAsJSON() }
						, columns: ${ columnData.encodeAsJSON() }
						, url: '${ (localData.attrs.url ?: '').encodeAsJavaScript() }'
						, dataUrl: '${ (localData.attrs.dataUrl != null ? localData.attrs.dataUrl : createLink(action: 'list') + '.json').encodeAsJavaScript() }'
						, reorderableColumns: ${ localData.attrs.reorderable ? 'true' : 'false'}
						, rows: ${ localData.attrs.rows }
						, dataLength: ${ localData.attrs.data.count }
					});
				});
			"""
		}
		out << "<div id=\"${ attrs.id }\" class=\"grid-container\"></div>"

		helpers.endTag()
	}

	def column = { attrs, body ->
		helpers.startTag(tag: 'column')

		// param attributes
		if (!attrs.containsKey('name')) {
			throwTagError('The column tag must have a name attribute')
		}
		if (!attrs.containsKey('label')) {
			attrs.label = attrs.name
			// TODO: humanize function
		}
		if (!attrs.containsKey('renderer')) {
			attrs.renderer = 'string'
		}
		if (!attrs.containsKey('sortable')) {
			attrs.sortable = true
		} else {
			attrs.sortable = attrs.sortable.toBoolean()
		}
		if (!attrs.containsKey('order')) {
			attrs.order = 'asc'
		}

		def localData = [attrs: attrs]
		helpers.updateTag(data: localData)

		helpers.endTag()
	}

	def field = { attrs, body ->
		helpers.startTag(tag: 'field')

		if (!attrs.containsKey('for')) {
			attrs.for = ''
		}
		if (!attrs.containsKey('label')) {
			attrs.label = attrs.for
			// TODO: humanize function
		}

		def localData = [attrs: attrs]
		helpers.updateTag(data: localData)

		// TODO: use the markup builder
		out << """\
			<div class=\"field-container\">
				<div class=\"label\">
					<label for=\"${ localData.attrs.for }\">${ localData.attrs.label }</label>
				</div>
				<div class=\"field\">
					${ body() }
				</div>
			</div>
		"""

		helpers.endTag()
	}

	def buttons = { attrs, body ->
		helpers.startTag(tag: 'buttons')

		def localData = [attrs: attrs]
		helpers.updateTag(data: localData)

		out << "<div class=\"buttons\">${body()}</div>"

		helpers.endTag()
	}

	def messages = { attrs, body ->
		helpers.startTag(tag: 'messages')

		def localData = [attrs: attrs]
		helpers.updateTag(data: localData)

		// run what we have inside our grid, but don't output (no need)
		body()

		def messageData = helpers.getChildTags().collect { if (it.tag == 'message') { it.data.attrs } }

		messageData = messageData.groupBy { it.type }

		out << "<div class=\"messages\">"

		messageData.each { type, messages ->
			def className = 'notifications'

			switch (type) {
				case 'info':
				case 'notifications':
				case 'notification':
					className = 'notifications'
					break;
				case 'error':
				case 'errors':
					className = 'errors'
					break;
				case 'success':
				case 'successes':
					className = 'successes'
					break;
				case 'warn':
				case 'warning':
				case 'warnings':
					className = 'warnings'
					break;
			}

			out << "<ul class=\"${className}\">"

			messages.each { message ->
				out << "<li>${ message.message }</li>"
			}

			out << "</ul>"
		}

		out << "</div>"

		helpers.endTag()
	}

	def message = { attrs, body ->
		helpers.startTag(tag: 'message')

		if (!attrs.containsKey('type')) {
			attrs.type = 'info'
		}
		if (!attrs.containsKey('message')) {
			attrs.message = body()
		}

		def localData = [attrs: attrs]
		helpers.updateTag(data: localData)

		helpers.endTag()
	}
}
