package com.chasonchoate.jsonapi.simple

import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Component
object LinkHelper {
    fun resourcesLink(route: String): String {
        val contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().build()
        return "$contextPath/${route.trim('/')}"
    }

    fun resourceLink(route: String, id: String): String {
        return "${resourcesLink(route)}/$id"
    }

    fun relationshipLink(route: String, id: String, relation: String): String {
        return "${resourcesLink(route)}/$id/relationships/$relation"
    }

    fun relatedLink(route: String, id: String, relation: String): String {
        return "${resourcesLink(route)}/$id/$relation"
    }
}
