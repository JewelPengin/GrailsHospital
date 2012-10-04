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

        def localData = [attrs: attrs]

        helpers.updateTag(data: localData)

        // run what we have inside our grid, but don't output (no need)
        body()

        def columnData = helpers.getChildTags().collect { if (it.tag == 'column') { it.data.attrs } }

        out << r.script() {
            out << """\
                \$(function(){
                    \$('#${ localData.attrs.id }').grid({
                        data: ${ localData.attrs.data.encodeAsJSON() }
                        , columns: ${ columnData.encodeAsJSON() }
                        , url: '${ (localData.attrs.url ?: '').encodeAsJavaScript() }'
                        , dataUrl: '${ (localData.attrs.dataUrl ?: '').encodeAsJavaScript() }'
                        , reorderableColumns: ${ localData.attrs.reorderable ? 'true' : 'false'}
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
        }
        if (!attrs.containsKey('renderer')) {
            attrs.renderer = 'string'
        }
        if (!attrs.containsKey('sortable')) {
            attrs.sortable = true
        }
        if (!attrs.containsKey('order')) {
            attrs.order = 'asc'
        }

        def localData = [attrs: attrs]

        helpers.updateTag(data: localData)

        helpers.endTag()
    }
}
