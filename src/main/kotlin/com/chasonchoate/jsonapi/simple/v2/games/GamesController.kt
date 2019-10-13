package com.chasonchoate.jsonapi.simple.v2.games

import com.chasonchoate.jsonapi.*
import com.chasonchoate.jsonapi.simple.v2.ResourceSerializer
import com.chasonchoate.jsonapi.simple.v2.reviews.ReviewsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/v2/games")
class GamesController {
    @Autowired
    lateinit var repo: GamesRepository
    @Autowired
    lateinit var reviewRepo: ReviewsRepository
    @Autowired
    lateinit var serializer: ResourceSerializer

    @GetMapping
    fun index(): JSONAPIResourcesDocument {
        val games = repo.findAll()
        return serializer.serialize(games)
    }
//    @PostMapping
//    fun create(): JSONAPIResourceDocument = repo.createOne

    @GetMapping("{id}")
    fun show(
            @PathVariable("id") id: String,
            @RequestParam("include", required = false, defaultValue = "") include: String?
    ): JSONAPIResourceDocument {
        val game = repo.findOne(id, include) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return serializer.serialize(game)
    }
//    @PatchMapping("{id}")
//    fun replace(@PathVariable("id") id: String): JSONAPIResourceDocument = repo.updateOne(id)
//    @DeleteMapping
//    fun delete(@PathVariable("id") id: String): JSONAPIResourceDocument = repo.deleteOne(id)

    @GetMapping("/{id}/relationships/review")
    fun showRelationship(@PathVariable("id") id: String): JSONAPIResourceHasOneRelationship {
        val game = repo.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val review = reviewRepo.findAll(game.id).firstOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val doc = JSONAPIResourceHasOneRelationship(serializer.toResourceIdentity(review))
        doc.links["self"] = game.relationships()["review"]?.relationshipRoute as String
        doc.links["related"] = game.relationships()["review"]?.relatedRoute as String
        return doc
    }
//     @PostMapping("/{id}/relationships/{relation}")
//     fun createRelationship() { ... }
//     @RequestMapping("/{id}/relationships/{relation}", methods = ["PUT", "PATCH"])
//     fun updateRelationship() { ... }
//     @DeleteMapping("/{id}/relationships/{relation}")
//     fun destroyRelationship() { ... }
//
    @GetMapping("/{id}/review")
    fun indexRelatedResources(@PathVariable("id") id: String): JSONAPIResourceDocument {
        val game = repo.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val review = reviewRepo.findAll(game.id).firstOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val doc = JSONAPIResourceDocument(serializer.toResource(review))
        doc.links["self"] = game.relationships()["review"]?.relatedRoute as String
        return doc
    }
}
