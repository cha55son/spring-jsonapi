package com.chasonchoate.jsonapi.repo_pattern.kg

import com.chasonchoate.jsonapi.repo_pattern.common.Resource

const val KGS_PATH = "/kgs"

class KG(id: String) : Resource(id, "kg", KGS_PATH) {
    var active: Boolean? = null

    override fun attributes() = listOf(this::active)
}
