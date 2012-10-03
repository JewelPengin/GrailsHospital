package com.centurylink.hospital

class HelpersTagLib {
    static namespace = 'helpers'
    static returnObjectForTags = ['startTag'
                                    , 'updateTag'
                                    , 'updateParentTag'
                                    , 'getTag'
                                    , 'getParentTag'
                                    , 'getChildTags'
                                    , 'endTag']

    def startTag = { attrs ->
        pageScope.customTags.depth++

        //println "starting tag ${ attrs.tag } in group ${ pageScope.customTags.group } at depth ${ pageScope.customTags.depth }"

        if (pageScope.customTags.tags[pageScope.customTags.group] == null) {
            pageScope.customTags.tags[pageScope.customTags.group] = []
        }
        if (pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth] == null) {
            pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth] = []
        }

        def order = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth].size()
        def parent = helpers.getParentTag()
        def parentOrder = null
        if (parent != false) {
            parentOrder = parent?.order
        }


        pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth].add([
                tag: attrs.tag
                , group: pageScope.customTags.group
                , depth: pageScope.customTags.depth
                , order: order
                , parentOrder: parentOrder
                , data: attrs.data ?: [:]
        ])

        return false
    }

    def updateTag = { attrs ->
        //println "Updating self looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
        def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth]
        if (tags != null) {
            tags[tags.size() - 1].data.putAll(attrs.data)
        }
        return false
    }

    def updateParentTag = { attrs ->
        //println "Updating parent looking for group ${ pageScope.customTags.group } and depth ${ (pageScope.customTags.depth - 1) }"
        def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth - 1]
        if (tags != null) {
            tags[tags.size() - 1].data.putAll(attrs.data)
        }
        return false
    }

    def getTag = { attrs ->
        //println "Getting self looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
        def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth]
        return tags[tags.size() - 1]
    }

    def getParentTag = { attrs ->
        //println "Getting parent looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
        if (pageScope.customTags.depth - 1 < 0) { return false }
        def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth - 1]
        //println 'found!'
        return tags[tags.size() - 1]
    }

    def getChildTags = { attrs ->
        //println "Getting children looking for group ${ pageScope.customTags.group } and depth ${ pageScope.customTags.depth }"
        def tags = pageScope.customTags.tags[pageScope.customTags.group][pageScope.customTags.depth + 1]
        if (tags == null) { return false }
        def self = helpers.getTag()
        return tags.collect { if (it.parentOrder == self.order) { it } }
    }

    def endTag = { attrs ->
        pageScope.customTags.depth--
        // once the depth gets back to -1 (or no parent custom tags) increment the grouping
        if (pageScope.customTags.depth == -1) {
            pageScope.customTags.group++
        }
        //println "Ending: setting depth to ${ pageScope.customTags.depth } and group to ${ pageScope.customTags.group }"
        return false
    }
}
