package com.chasonchoate.jsonapi

import com.chasonchoate.jsonapi.kg.KGsController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping

@Configuration
class WebConfig {
    @Bean
    fun simpleUrlHandlerMapping(): SimpleUrlHandlerMapping {
        val simpleUrlHandlerMapping = SimpleUrlHandlerMapping()
        simpleUrlHandlerMapping.order = 2

        val urlMap = HashMap<String, Any>()
        urlMap["/kgs"] = KGsController()
        simpleUrlHandlerMapping.urlMap = urlMap
        return simpleUrlHandlerMapping
    }
}
