package com.chasonchoate.jsonapi.kg

import com.chasonchoate.jsonapi.common.Resource
import kotlin.reflect.KProperty

const val KGS_PATH = "/kgs"

class KG(id: String) : Resource(id, "kg", KGS_PATH) {
    var active: Boolean? = null

    override fun attributes() = listOf(this::active)
}
