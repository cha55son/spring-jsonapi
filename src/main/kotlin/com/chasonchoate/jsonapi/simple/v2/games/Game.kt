package com.chasonchoate.jsonapi.simple.v2.games

import com.chasonchoate.jsonapi.simple.v2.HasOneRelationship
import com.chasonchoate.jsonapi.simple.v2.Resource
import com.chasonchoate.jsonapi.simple.v2.reviews.Review
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class Game(id: String, var name: String) : Resource(id, "game") {
    var review: Review? = null

    override fun attributes() = listOf(this::name)
    override fun relationships() = mapOf(
            "review" to HasOneRelationship(this::review,
                    relationshipRoute = linkTo(methodOn(GamesController::class.java).showRelationship(id)).toString(),
                    relatedRoute = linkTo(methodOn(GamesController::class.java).indexRelatedResources(id)).toString()
            )
    )
}
