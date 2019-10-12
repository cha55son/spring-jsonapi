package com.chasonchoate.jsonapi.simple

import com.chasonchoate.jsonapi.JSONAPIResource
import com.chasonchoate.jsonapi.JSONAPIResourceDocument
import com.chasonchoate.jsonapi.simple.LinkHelper.resourceLink
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

val attractions = listOf(
        Attraction("1", "Stones River Mall", "1"),
        Attraction("2", "Avenues", "1"),
        Attraction("3", "Tennessee Tech", "2"),
        Attraction("4", "Shooting Range", "2"),
        Attraction("5", "Broadway", "3"),
        Attraction("6", "Bridgestone Arena", "3"),
        Attraction("7", "Countryside", "4"),
        Attraction("8", "Spring Hill North", "4")
)

/**
 * Demonstrates a nested resource that does not live at the root
 */
@RestController
@RequestMapping(Attraction.ROUTE)
class AttractionsController {
    /**
     * NOTE: Nested resources should not implement the `index` method
     *       as it would override the parent class' `indexRelatedResources` method.
     */
    // fun index()...

    @GetMapping("/{id}")
    fun show(
            @PathVariable("cityId") cityId: String,
            @PathVariable("id") id: String
    ): JSONAPIResourceDocument {
        val attraction = attractions.find { it.id == id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val res = attraction.toResource()
        // Move the resource links to the document
        val links = res.links
        res.links = mutableMapOf()
        val doc = JSONAPIResourceDocument(res)
        doc.links = links
        return doc
    }
}


data class Attraction(val id: String, val name: String, val cityId: String) {
    companion object {
        const val TYPE = "attraction"
        const val ROUTE = "/cities/{cityId}/attractions"
    }
    fun toResource(): JSONAPIResource {
        val res = JSONAPIResource(id, TYPE)
        res.attributes["name"] = name
        res.links["self"] = resourceLink(ROUTE, id, mapOf("cityId" to cityId))
        return res
    }
}
