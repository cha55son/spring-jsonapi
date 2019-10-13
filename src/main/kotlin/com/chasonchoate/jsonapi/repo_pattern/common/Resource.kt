package com.chasonchoate.jsonapi.repo_pattern.common

import com.chasonchoate.jsonapi.JSONAPIResource
import com.chasonchoate.jsonapi.JSONAPIResourceRelationshipBase
import javax.servlet.http.HttpServletRequest
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

enum class ResourceRelationshipType {
    HAS_ONE,
    HAS_MANY
}
class ResourceRelationship(val type: ResourceRelationshipType)

abstract class Resource(var id: String, var type: String, var route: String) {
    /**
     * TODO: This will probably change to annotations or something
     */
    abstract fun attributes(): List<KProperty<*>>
    open fun relationships(): Map<String, ResourceRelationship> = emptyMap()

    fun toResource(req: HttpServletRequest): JSONAPIResource {
        val res = JSONAPIResource(id, type)
        parseLinks(res, req)
        parseAttributes(res, req)
        parseRelationships(res, req)
        return res
    }

    private fun parseLinks(res: JSONAPIResource, req: HttpServletRequest) {
        res.links["self"] = selfLink(req)
    }

    private fun parseAttributes(res: JSONAPIResource, req: HttpServletRequest) {
        val attributes = mutableMapOf<String, Any>()
        attributes().forEach { prop ->
            (prop as KProperty0).get()?.let {
                attributes[prop.name] = it
            }
        }
        if (attributes.isNotEmpty()) {
            res.attributes = attributes
        }
    }

    private fun parseRelationships(res: JSONAPIResource, req: HttpServletRequest) {
        val relationships = mutableMapOf<String, JSONAPIResourceRelationshipBase>()
        relationships().forEach { (key) ->
            val doc = JSONAPIResourceRelationshipBase()
            doc.links["self"] = relationshipLink(key, req)
            doc.links["related"] = relatedLink(key, req)
            relationships[key] = doc
        }
        if (relationships.isNotEmpty()) {
           res.relationships = relationships
        }
    }

    private fun relationshipLink(relation: String, req: HttpServletRequest): String {
        return selfLink(req) + "/relationships/$relation"
    }

    private fun relatedLink(relation: String, req: HttpServletRequest): String {
        return selfLink(req) + "/$relation"
    }

    private fun selfLink(req: HttpServletRequest): String {
        val root = req.requestURL.toString().split(req.servletPath).first()
        return "$root/${route.trim('/')}/$id"
    }

    /**
     * Methods for altering the JSONAPIResource directly could be useful here.
     *
     * addAttributes() { ... }
     * addLinks() { ... }
     * addMeta() { ... }
     */
}
