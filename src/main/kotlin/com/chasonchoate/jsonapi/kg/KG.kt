package com.chasonchoate.jsonapi.kg

import com.chasonchoate.jsonapi.common.Resource

class KG(id: String) : Resource(id, "kg") {
    var active: Boolean? = null

    override fun attributes() = listOf(this::active)
}
