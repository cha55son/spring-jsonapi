package com.chasonchoate.jsonapi.simple.v2.reviews

import com.chasonchoate.jsonapi.JSONAPIResourceDocument
import com.chasonchoate.jsonapi.simple.v2.ResourceSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * NESTED ROUTE EXAMPLE
 */
@RestController
@RequestMapping("/v2/games/{gameId}/reviews")
class ReviewsController {
    @Autowired
    lateinit var repo: ReviewsRepository
    @Autowired
    lateinit var serializer: ResourceSerializer

//    @GetMapping
//    fun index(@PathVariable): JSONAPIResourcesDocument {
//        val reviews = repo.findAll()
//        reviews.forEach {
//            it.addLink("self", linkTo(methodOn(ReviewsController::class.java).show(it.id)).toString())
//        }
//        return serializer.serialize(reviews)
//    }
//    @PostMapping
//    fun create(): JSONAPIResourceDocument = repo.createOne

    @GetMapping("{id}")
    fun show(
            @PathVariable("gameId") gameId: String,
            @PathVariable("id") id: String
    ): JSONAPIResourceDocument {
        val review = repo.findOne(gameId, id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        review.addLink("self", linkTo(methodOn(ReviewsController::class.java).show(review.gameId, review.id)).toString())
        return serializer.serialize(review)
    }
}
