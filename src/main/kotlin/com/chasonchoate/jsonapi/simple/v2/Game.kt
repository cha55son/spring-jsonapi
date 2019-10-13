package com.chasonchoate.jsonapi.simple.v2

import kotlin.reflect.KProperty

open class Resource(val id: String, val type: String) {
    private val links = mutableMapOf<String, String>()

    open fun attributes() = emptyList<KProperty<*>>()
    fun addLink(name: String, route: String) { links[name] = route }
    fun getLinks(): Map<String, String> = links
}

class Game(id: String, val name: String) : Resource(id, "game") {

    override fun attributes() = listOf(this::name)
}
