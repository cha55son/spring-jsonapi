package com.chasonchoate.jsonapi.simple

import com.chasonchoate.jsonapi.JSONAPIResource
import com.chasonchoate.jsonapi.JSONAPIResourceDocument
import com.chasonchoate.jsonapi.JSONAPIResourcesDocument
import com.chasonchoate.jsonapi.simple.LinkHelper.resourceLink
import com.chasonchoate.jsonapi.simple.LinkHelper.resourcesLink
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

val locations = listOf(
        FavoriteLocation("1", "MTSU", "1"),
        FavoriteLocation("2", "Avenues", "1"),
        FavoriteLocation("3", "T-Mart", "2"),
        FavoriteLocation("4", "Tech Campus", "2"),
        FavoriteLocation("5", "Otaku South", "3"),
        FavoriteLocation("6", "Percy Warner", "3"),
        FavoriteLocation("7", "Whit's Frozen Custard", "4"),
        FavoriteLocation("8", "Canterbury Neighborhood", "4")
)

/**
 * Demonstrates a related resource that lives at the root.
 */
@RestController
@RequestMapping(FavoriteLocation.ROUTE)
class FavoriteLocationsController {
    @GetMapping
    fun index(): JSONAPIResourcesDocument {
        val doc = JSONAPIResourcesDocument(locations.map { it.toResource() })
        doc.links["self"] = resourcesLink(FavoriteLocation.ROUTE)
        return doc
    }
    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: String): JSONAPIResourceDocument {
        val loc = locations.find { it.id == id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val doc = JSONAPIResourceDocument(loc.toResource())
        doc.links["self"] = resourceLink(FavoriteLocation.ROUTE, id)
        return doc
    }
}

data class FavoriteLocation(val id: String, val name: String, val cityId: String) {
    companion object {
        const val TYPE = "favorite-location"
        const val ROUTE = "/favorite-locations"
    }
    fun toResource(): JSONAPIResource {
        val res = JSONAPIResource(id, TYPE)
        res.attributes["name"] = name
        res.links["self"] = resourceLink(ROUTE, id)
        return res
    }
}

