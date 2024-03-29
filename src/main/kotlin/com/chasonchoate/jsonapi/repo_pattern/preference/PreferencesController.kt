package com.chasonchoate.jsonapi.repo_pattern.preference

import com.chasonchoate.jsonapi.repo_pattern.common.JSONAPIController
import com.chasonchoate.jsonapi.JSONAPIResourceID
import com.chasonchoate.jsonapi.repo_pattern.common.ResourceController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

@JSONAPIController
@RequestMapping(PREFERENCES_PATH)
class PreferencesController : ResourceController<Preference>() {
    val prefs by lazy {
        val pref = Preference("1")
        listOf(pref)
    }

    override fun index(): List<Preference> {
        return prefs
    }
    override fun show(id: String): Preference {
        return prefs.find { it.id == id } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource")
    }

    override fun showRelationship(id: String, relationship: String): List<JSONAPIResourceID> {
        return listOf(JSONAPIResourceID("1", "kg"))
    }
}
