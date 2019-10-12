package com.chasonchoate.jsonapi.simple

import com.chasonchoate.jsonapi.*
import com.chasonchoate.jsonapi.simple.LinkHelper.relatedLink
import com.chasonchoate.jsonapi.simple.LinkHelper.relationshipLink
import com.chasonchoate.jsonapi.simple.LinkHelper.resourceLink
import com.chasonchoate.jsonapi.simple.LinkHelper.resourcesLink
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

val cities = listOf(
        City("1", "Murfreesboro", false),
        City("2", "Cookeville", false),
        City("3", "Nashville", false),
        City("4", "Thompson's Station", true)
)

@RestController
@RequestMapping(City.ROUTE)
class CitiesController {
    @GetMapping
    fun index(): JSONAPIResourcesDocument {
        val doc = JSONAPIResourcesDocument(cities.map { it.toResource() })
        doc.links = mapOf("self" to resourcesLink(City.ROUTE))
        return doc
    }
    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: String): JSONAPIResourceDocument {
        val city = cities.find { it.id == id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val doc = JSONAPIResourceDocument(city.toResource())
        doc.links = mapOf("self" to resourceLink(City.ROUTE, city.id))
        return doc
    }
    @GetMapping("/{id}/relationships/${City.FAV_LOC_RELATION}")
    fun showRelationship(@PathVariable("id") id: String): JSONAPIResourceHasManyRelationship {
        val locations = locations.filter { it.cityId == id }.map {
            JSONAPIResourceID(it.id, FavoriteLocation.TYPE)
        }
        val doc = JSONAPIResourceHasManyRelationship(locations)
        doc.links = mapOf(
                "self" to relationshipLink(City.ROUTE, id, City.FAV_LOC_RELATION),
                "related" to relatedLink(City.ROUTE, id, City.FAV_LOC_RELATION)
        )
        return doc
    }
    @GetMapping("/{id}/favoriteLocations")
    fun indexRelatedResources(@PathVariable("id") id: String): JSONAPIResourcesDocument {
        val locs = locations.filter { it.cityId == id }.map { it.toResource() }
        val doc = JSONAPIResourcesDocument(locs)
        doc.links = mapOf("self" to relatedLink(City.ROUTE, id, City.FAV_LOC_RELATION))
        return doc
    }
}

data class City(val id: String, val name: String, val current: Boolean) {
    companion object {
        const val TYPE = "city"
        const val ROUTE = "/cities"
        const val FAV_LOC_RELATION = "favoriteLocations"
    }
    fun toResource(): JSONAPIResource {
        val res = JSONAPIResource(id, TYPE)
        res.attributes = mapOf(
                "name" to name,
                "current" to current
        )
        val favLocs = JSONAPIResourceRelationshipBase()
        favLocs.links = mapOf(
                "self" to relationshipLink(ROUTE, id, FAV_LOC_RELATION),
                "related" to relatedLink(ROUTE, id, FAV_LOC_RELATION)
        )
        res.relationships = mapOf(FAV_LOC_RELATION to favLocs)
        res.links = mapOf("self" to resourceLink(ROUTE, id))
        return res
    }
}
