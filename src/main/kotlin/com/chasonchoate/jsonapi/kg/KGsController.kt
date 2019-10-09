package com.chasonchoate.jsonapi.kg

import com.chasonchoate.jsonapi.common.ResourceController
import org.springframework.web.bind.annotation.RestController

@RestController // TODO: Get rid of this
class KGsController : ResourceController<KG>() {
    // GET /kgs
    override fun index(): List<KG> {
        val kg1 = KG("1")
        kg1.active = false
        return listOf(kg1, KG("2"))
    }
    // fun show() { ... }                   // GET /kgs/:id
    // fun showRelationship() { ... }       // GET /kgs/:id/relationships/comms
}
