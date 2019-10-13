package com.chasonchoate.jsonapi.simple.v2.reviews

import org.springframework.stereotype.Repository

@Repository
class ReviewsRepository {
    val reviews = listOf(
            Review("1", "1", true),
            Review("2", "2", false)
    )

    fun findAll(gameId: String) = reviews.filter { it.gameId == gameId }
    fun findOne(gameId: String, id: String) = findAll(gameId).find { it.id == id }
}
