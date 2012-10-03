package com.centurylink.hospital

import grails.converters.JSON

class CenturyLinkTagLib {
    static namespace = 'cl'
    static returnObjectForTags = ['helper']

    def helper = { attrs ->
        def tagsSize = pageScope.customTags.tags.size()

        switch (attrs.event) {
            case 'start':
                pageScope.customTags.depth++

                println "starting tag ${ attrs.tag } in group ${ pageScope.customTags.group } at depth ${ pageScope.customTags.depth }"

                if (pageScope.customTags.tags[pageScope.customTags.group] == null) {
                    pageScope.customTags.tags[pageScope.customTags.group] = []
                }
                if (pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth] == null) {
                    pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth] = []
                }

                def order = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth].size()
                def parent = cl.helper(event: 'getParent')
                println parent
                def parentOrder = null
                if (parent != null) {
                    parentOrder = parent.order
                }

                pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth].add([
                        tag: attrs.tag
                        , group: pageScope.customTags.group
                        , depth: pageScope.customTags.depth
                        , order: order
                        , parentOrder: parentOrder
                        , data: attrs.data ?: [:]
                ])
                break;
            case 'update':
                //println "Updating self looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
                def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth]
                if (tags != null) {
                    tags[tags.size() - 1].data.putAll(attrs.data)
                }
                break;
            case 'updateParent':
                //println "Updating parent looking for group ${ pageScope.customTags.group } and depth ${ (pageScope.customTags.depth - 1) }"
                def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth - 1]
                if (tags != null) {
                    tags[tags.size() - 1].data.putAll(attrs.data)
                }
                break;
            case 'get':
                //println "Getting self looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
                def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth]
                return tags[tags.size() - 1]
                break;
            case 'getParent':
                //println "Getting parent looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
                if (pageScope.customTags.depth - 1 < 0) { break; }
                def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth - 1]
                return tags[tags.size() - 1]
                break;
            case 'getChildren':
                //println "Getting children looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
                def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth + 1]
                if (tags == null) { break; }
                def self = cl.helper(event: 'get')
                return tags.collect { it.parentOrder == self.order }
                break;
            case 'end':
                pageScope.customTags.depth--
                // once the depth gets back to -1 (or no parent custom tags) increment the grouping
                if (pageScope.customTags.depth == -1) {
                    pageScope.customTags.group++
                }
                //println "Ending: setting depth to ${ pageScope.customTags.depth } and group to ${ pageScope.customTags.group }"
                break;
        }

        return null
    }

    def grid = { attrs, body ->
        cl.helper(event: 'start', tag: 'grid')

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

        def localData = [attrs: attrs, columns: []]

        cl.helper(event: 'update', data: localData)

        // run what we have inside our grid, but don't output (no need)
        body()

        def columnData = localData.columns.collect { it.attrs }

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

        cl.helper(event: 'end')
    }

    def column = { attrs, body ->
        cl.helper(event: 'start', tag: 'column')

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

        def localData = [attrs: attrs]

        cl.helper(event: 'update', data: localData)
        def parentData = cl.helper(event: 'getParent')

        parentData.data.columns.add(localData)

        cl.helper(event: 'end')
    }
}
