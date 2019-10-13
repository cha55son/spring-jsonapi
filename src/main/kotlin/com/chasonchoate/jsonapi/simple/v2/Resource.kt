package com.chasonchoate.jsonapi.simple.v2

import kotlin.reflect.KProperty

open class Resource(val id: String, val type: String) {
    private val links = mutableMapOf<String, String>()

    open fun attributes() = emptyList<KProperty<Any?>>()
    open fun relationships() = emptyMap<String, ResourceRelationship>()
    fun addLink(name: String, route: String) { links[name] = route }
    fun getLinks(): Map<String, String> = links
}

open class ResourceRelationship(val prop: KProperty<*>, val relationshipRoute: String?, val relatedRoute: String?)

class HasManyRelationship(prop: KProperty<*>, relationshipRoute: String?, relatedRoute: String?) : ResourceRelationship(prop, relationshipRoute, relatedRoute)
class HasOneRelationship(prop: KProperty<*>, relationshipRoute: String?, relatedRoute: String?) : ResourceRelationship(prop, relationshipRoute, relatedRoute)

