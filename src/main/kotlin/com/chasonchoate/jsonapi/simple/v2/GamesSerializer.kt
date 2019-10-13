package com.chasonchoate.jsonapi.simple.v2

import com.chasonchoate.jsonapi.JSONAPIResource
import com.chasonchoate.jsonapi.JSONAPIResourceDocument
import com.chasonchoate.jsonapi.JSONAPIResourcesDocument
import org.springframework.stereotype.Component

@Component
class GamesSerializer {
    fun serialize(input: Game): JSONAPIResourceDocument {
        val res = toResource(input)
        // Copy resource links onto the document
        val links = res.links
        res.links = mutableMapOf()
        val doc = JSONAPIResourceDocument(res)
        doc.links = links
        return doc
    }
    fun serialize(input: List<Game>): JSONAPIResourcesDocument {
        val resources = input.map { toResource(it) }
        val doc = JSONAPIResourcesDocument(resources)
        return doc
    }

    private fun toResource(input: Game): JSONAPIResource {
        val res = JSONAPIResource(input.id, input.type)
        input.attributes().forEach { attr ->
            res.attributes[attr.name] = attr.get()
        }
        input.getLinks().forEach { (name, route) ->
            res.links[name] = route
        }
        return res
    }
}
