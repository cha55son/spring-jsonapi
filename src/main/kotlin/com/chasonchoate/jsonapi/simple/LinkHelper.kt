package com.chasonchoate.jsonapi.simple

import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder

@Component
object LinkHelper {
    fun resourcesLink(route: String, params: Map<String, String> = emptyMap()): String {
        val expandedRoute = UriComponentsBuilder
                .fromPath(route)
                .buildAndExpand(params).toString()
        val contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().build()
        return "$contextPath/${expandedRoute.trim('/')}"
    }

    fun resourceLink(route: String, id: String, params: Map<String, String> = emptyMap()): String {
        return "${resourcesLink(route, params)}/$id"
    }

    fun relationshipLink(route: String, id: String, relation: String, params: Map<String, String> = emptyMap()): String {
        return "${resourcesLink(route, params)}/$id/relationships/$relation"
    }

    fun relatedLink(route: String, id: String, relation: String, params: Map<String, String> = emptyMap()): String {
        return "${resourcesLink(route, params)}/$id/$relation"
    }
}
