package com.chasonchoate.jsonapi.simple.v2

import org.springframework.stereotype.Repository

@Repository
class GamesRepository {
    val games = listOf(
            Game("1", "Rocket League"),
            Game("2", "Battlefield")
    )

    fun findAll() = games
    fun findOne(id: String) = games.find { it.id == id }
}
