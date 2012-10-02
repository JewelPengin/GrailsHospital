package hospital

import grails.converters.JSON

class CenturyLinkTagLib {
    def grid = { attrs, body ->

        // param attributes
        if (!attrs.containsKey('name')) {
            throwTagError('The grid tag must have a name attribute')
        }
        if (!attrs.containsKey('data')) {
            throwTagError('The grid tag must have a data attribute')
        }
        if (!attrs.containsKey('id')) {
            attrs.id = attrs.name
        }

        pageScope.gridColumns = []

        // run what we have inside our grid, but don't output (no need)
        body()

        out << r.script() {
            out << """\
                \$(function(){
                    \$('#${attrs.id}').grid({
                        data: ${ attrs.data.encodeAsJSON() }
                        , columns: ${ pageScope.gridColumns.encodeAsJSON() }
                        , url: '${ (attrs.url ?: '').encodeAsJavaScript() }'
                        , dataUrl: '${ (attrs.dataUrl ?: '').encodeAsJavaScript() }'
                        , reorderableColumns: ${ attrs.reorderable ? 'true' : 'false'}
                    });
                });
            """
        }
        out << "<div id=\"${ attrs.id }\"></div>"
    }

    def column = { attrs, body ->

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

        pageScope.gridColumns.add(attrs)
    }
}
