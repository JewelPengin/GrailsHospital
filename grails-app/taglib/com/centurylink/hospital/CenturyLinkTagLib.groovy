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
                        , dataUrl: '${ (localData.attrs.dataUrl ?: createLink(action: 'list') + '.json').encodeAsJavaScript() }'
                        , reorderableColumns: ${ localData.attrs.reorderable ? 'true' : 'false'}
                        , rows: ${ localData.attrs.rows }
                        , dataLength: ${ localData.attrs.data.count }
                    });
                });
            """
        }
        out << "<div id=\"${ attrs.id }\"></div>"

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
}
