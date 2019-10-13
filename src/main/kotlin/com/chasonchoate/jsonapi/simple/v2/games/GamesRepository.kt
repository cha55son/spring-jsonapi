package com.chasonchoate.jsonapi.simple.v2.games

import com.chasonchoate.jsonapi.simple.v2.Resource
import com.chasonchoate.jsonapi.simple.v2.reviews.ReviewsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.stereotype.Repository

@Repository
class GamesRepository {
    @Autowired
    lateinit var reviewsRepo: ReviewsRepository

    val games = listOf(
            Game("1", "Rocket League"),
            Game("2", "Battlefield")
    )

    fun findAll() = addSelfLink(games)
    fun findOne(id: String): Game? {
        val game = games.find { it.id == id } ?: return null
        addSelfLink(game)
        return game
    }
    fun findOne(id: String, includeRelation: String?): Game? {
        val game = findOne(id) ?: return null
        setRelated(game, includeRelation)
        return game
    }

    private fun setRelated(game: Game, relation: String? = "") {
        if (!game.relationships().contains(relation)) { return }

        game.review = reviewsRepo.findAll(game.id).firstOrNull()
    }
    private fun addSelfLink(games: List<Game>) = games.map { addSelfLink(it) }
    private fun addSelfLink(game: Game): Game {
        game.addLink("self", linkTo(methodOn(GamesController::class.java).show(game.id, null)).withSelfRel().expand().href)
        return game
    }
}
