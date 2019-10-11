package com.chasonchoate.jsonapi.preference

import com.chasonchoate.jsonapi.common.Resource
import com.chasonchoate.jsonapi.common.ResourceRelationship
import com.chasonchoate.jsonapi.common.ResourceRelationshipType

const val PREFERENCES_PATH = "/preferences"

class Preference(id: String) : Resource(id, "preference", PREFERENCES_PATH) {
    var darkMode: Boolean? = null

    override fun attributes() = listOf(this::darkMode)
    override fun relationships() = mapOf(
            "kg" to ResourceRelationship(ResourceRelationshipType.HAS_ONE)
    )
}
