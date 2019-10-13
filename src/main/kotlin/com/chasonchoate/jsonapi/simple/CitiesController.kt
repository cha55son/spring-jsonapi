package com.chasonchoate.jsonapi.simple

import com.chasonchoate.jsonapi.*
import com.chasonchoate.jsonapi.simple.LinkHelper.relatedLink
import com.chasonchoate.jsonapi.simple.LinkHelper.relationshipLink
import com.chasonchoate.jsonapi.simple.LinkHelper.resourceLink
import com.chasonchoate.jsonapi.simple.LinkHelper.resourcesLink
import org.omg.CosNaming.NamingContextPackage.NotFound
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.lang.RuntimeException

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
    fun show(@PathVariable("id") id: String, @RequestParam("include") include: String): JSONAPIResourceDocument {
        val city = cities.find { it.id == id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val res = city.toResource()
        /**
         * Run the sparse field check, this will influence the includes below
         */
        // ...
        /**
         * Adding included resources
         */
        val doc = JSONAPIResourceDocument(res)
        val included = include.split(",")
        if (included.isNotEmpty()) {
            val includedResources = mutableListOf<JSONAPIResource>()
            included.forEach {
                /**
                 * Needs to be recursive on the resource
                 */
                val includedTokens = it.split(".")
                /**
                 * for each include path
                 *   resource.includeResources(acc, path.split("."))
                 *     bail if there are no tokens
                 *     resources = get all resources for relation tokens[0]
                 *     acc.addAll(resources)
                 *     Update it.relation data
                 *     resources.forEach { it.includeResources(acc, tokens[1..-1]) }
                 *
                 */
                when (it) {
                    City.RELATIONS[0] -> {
                        val resources = indexRelatedResourcesLocations(id)
                        includedResources.addAll(resources)
                        val prevRel = res.relationships[City.RELATIONS[0]]!!
                        val rel = JSONAPIResourceHasManyRelationship(resources.map {
                            JSONAPIResourceID(it.id, it.type)
                        })
                        rel.links = prevRel.links
                        rel.meta = prevRel.meta
                        res.relationships[City.RELATIONS[0]] = rel
                        // Update the primary resource so that the relation has the correct resource ids.

                    }
                    City.RELATIONS[1] -> {
                        val resources = indexRelatedResourcesAttractions(id)
                        includedResources.addAll(resources)
                        val prevRel = res.relationships[City.RELATIONS[1]]!!
                        val rel = JSONAPIResourceHasManyRelationship(resources.map {
                            JSONAPIResourceID(it.id, it.type)
                        })
                        rel.links = prevRel.links
                        rel.meta = prevRel.meta
                        res.relationships[City.RELATIONS[1]] = rel
                        // Update the primary resource so that the relation has the correct resource ids.
                    }
                    else -> throw RuntimeException("include param does not match a relation")
                }
            }
            doc.included = includedResources
        }
        // Move the resource links to the document
        val links = res.links
        res.links = mutableMapOf()
        doc.links = links
        return doc
    }
    @GetMapping("/{id}/relationships/{relation}")
    fun showRelationship(
            @PathVariable("id") id: String,
            @PathVariable("relation") relation: String
    ): JSONAPIResourceHasManyRelationship {
        if (!City.RELATIONS.contains(relation)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        val resIds = when(relation) {
            City.RELATIONS[0] -> showRelationshipLocations(id)
            City.RELATIONS[1] -> showRelationshipAttractions(id)
            else -> throw RuntimeException("bad relation")
        }
        val doc = JSONAPIResourceHasManyRelationship(resIds)
        doc.links = mapOf(
                "self" to relationshipLink(City.ROUTE, id, relation),
                "related" to relatedLink(City.ROUTE, id, relation)
        )
        return doc
    }
    @GetMapping("/{id}/{relation}")
    fun indexRelatedResources(
            @PathVariable("id") id: String,
            @PathVariable("relation") relation: String
    ): JSONAPIResourcesDocument {
        if (!City.RELATIONS.contains(relation)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        val resources = when(relation) {
            City.RELATIONS[0] -> indexRelatedResourcesLocations(id)
            City.RELATIONS[1] -> indexRelatedResourcesAttractions(id)
            else -> throw RuntimeException("bad relation")
        }
        val doc = JSONAPIResourcesDocument(resources)
        doc.links = mapOf("self" to relatedLink(City.ROUTE, id, relation))
        return doc
    }

    private fun showRelationshipLocations(id: String): List<JSONAPIResourceID> {
        return locations.filter { it.cityId == id }.map {
            JSONAPIResourceID(it.id, FavoriteLocation.TYPE)
        }
    }
    private fun showRelationshipAttractions(id: String): List<JSONAPIResourceID> {
        return attractions.filter { it.cityId == id }.map {
            JSONAPIResourceID(it.id, Attraction.TYPE)
        }
    }
    private fun indexRelatedResourcesLocations(id: String): List<JSONAPIResource> {
        return locations.filter { it.cityId == id }.map { it.toResource() }
    }
    private fun indexRelatedResourcesAttractions(id: String): List<JSONAPIResource> {
        return attractions.filter { it.cityId == id }.map { it.toResource() }
    }
}

/**
 * Need some kind of annotation to prevent resources of the same type
 */
data class City(val id: String, val name: String, val current: Boolean) {
    companion object {
        const val TYPE = "city"
        const val ROUTE = "/cities"
        val RELATIONS = arrayOf("favoriteLocations", "attractions")
    }
    fun toResource(): JSONAPIResource {
        val res = JSONAPIResource(id, TYPE)
        /**
         * Should not be able to have attributes and relationships with the same name.
         * Attributes cannot have a key with `relationships` or `links`.
         */
        res.attributes["name"] = name
        res.attributes["current"] = current
        RELATIONS.forEach {
            val rel = JSONAPIResourceRelationshipBase()
            rel.links = mapOf(
                    "self" to relationshipLink(ROUTE, id, it),
                    "related" to relatedLink(ROUTE, id, it)
            )
            res.relationships[it] = rel
        }
        res.links["self"] = resourceLink(ROUTE, id)
        return res
    }
}
