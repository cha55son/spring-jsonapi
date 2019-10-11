package com.chasonchoate.jsonapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JsonapiApplication

fun main(args: Array<String>) {
	runApplication<JsonapiApplication>(*args)
}

/**
 * jsonapi
 * 	.root { doc ->
 * 		doc.links.put("custom", "some path")
 * 	}
 */
