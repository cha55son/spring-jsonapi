package com.chasonchoate.jsonapi.common

import javax.servlet.http.HttpServletRequest
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

abstract class Resource(var id: String, var type: String) {
    /**
     * TODO: This will probably change to annotations or something
     */
    abstract fun attributes(): List<KProperty<*>>

    fun toResource(req: HttpServletRequest? = null): JSONAPIResource {
        val res = JSONAPIResource(id, type)
        val attributes = mutableMapOf<String, Any>()
        attributes().forEach { prop ->
            (prop as KProperty0).get()?.let {
                attributes[prop.name] = it
            }
        }
        if (attributes.isNotEmpty()) {
            res.attributes = attributes
        }
        req?.let {
            res.links = mapOf("self" to req.requestURL.toString() + '/' + id)
        }
        return res
    }

    /**
     * Methods for altering the JSONAPIResource directly could be useful here.
     *
     * addAttributes() { ... }
     * addLinks() { ... }
     * addMeta() { ... }
     */
}
