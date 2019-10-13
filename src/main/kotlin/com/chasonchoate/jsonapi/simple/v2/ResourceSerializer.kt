package com.chasonchoate.jsonapi.simple.v2

import com.chasonchoate.jsonapi.*
import org.springframework.stereotype.Component
import kotlin.reflect.KProperty0

@Component
class ResourceSerializer {
    fun serialize(input: Resource): JSONAPIResourceDocument {
        val includedResources = discoverIncludedResources(input).map { toResource(it) }
        val res = toResource(input)
        // Copy resource links onto the document
        val links = res.links
        res.links = mutableMapOf()
        val doc = JSONAPIResourceDocument(res)
        doc.included.addAll(includedResources)
        doc.links = links
        return doc
    }
    fun serialize(input: List<Resource>): JSONAPIResourcesDocument {
        val resources = input.map { toResource(it) }
        val doc = JSONAPIResourcesDocument(resources)
        return doc
    }

    fun discoverIncludedResources(input: Resource): List<Resource> {
        val included = mutableListOf<Resource>()
        input.relationships().forEach { (key, relProp) ->
            when(relProp) {
                is HasManyRelationship -> {
                    val relPropVal = (relProp.prop as KProperty0<List<Resource>>).get()
                    included.addAll(relPropVal)
                }
                is HasOneRelationship -> {
                    val relPropVal = (relProp.prop as KProperty0<Resource>).get()
                    included.add(relPropVal)
                }
                else -> throw RuntimeException("Unknown relationship type")
            }
        }
        return included
    }

    fun toResource(input: Resource): JSONAPIResource {
        val res = JSONAPIResource(input.id, input.type)
        input.attributes().forEach { attr ->
            res.attributes[attr.name] = (attr as KProperty0<Any>).get()
        }
        input.relationships().forEach { (key, relProp) ->
            val rel: JSONAPIResourceRelationshipBase = when(relProp) {
                is HasManyRelationship -> {
                    val relPropVal = (relProp.prop as KProperty0<List<Resource>>).get()
                    JSONAPIResourceHasManyRelationship(relPropVal.map { toResourceIdentity(it) })
                }
                is HasOneRelationship -> {
                    val relPropVal = (relProp.prop as KProperty0<Resource>).get()
                    JSONAPIResourceHasOneRelationship(toResourceIdentity(relPropVal))
                }
                else -> throw RuntimeException("Unknown relationship type")
            }
            rel.links["self"] = relProp.relationshipRoute!!
            rel.links["related"] = relProp.relatedRoute!!
            res.relationships[key] = rel
        }
        input.getLinks().forEach { (name, route) ->
            res.links[name] = route
        }
        return res
    }

    fun toResourceIdentity(input: Resource) = JSONAPIResourceID(input.id, input.type)
}
