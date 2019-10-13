package com.chasonchoate.jsonapi.simple.v2.reviews

import com.chasonchoate.jsonapi.simple.v2.Resource

class Review(id: String, val gameId: String, val positive: Boolean) : Resource(id, "review") {

    override fun attributes() = listOf(this::positive)
}

