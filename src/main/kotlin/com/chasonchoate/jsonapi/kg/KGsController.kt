package com.chasonchoate.jsonapi.kg

import com.chasonchoate.jsonapi.common.JSONAPIController
import com.chasonchoate.jsonapi.common.ResourceController
import org.springframework.web.bind.annotation.RequestMapping

@JSONAPIController
@RequestMapping("/kgs")
class KGsController : ResourceController<KG>() {
    // GET /kgs
    override fun index(): List<KG> {
        val kg1 = KG("1")
        kg1.active = false
        return listOf(kg1, KG("2"))
    }
    override fun show(id: String): KG {
        val kg1 = KG("1")
        kg1.active = false
        return kg1
    }
    // fun showRelationship() { ... }       // GET /kgs/:id/relationships/comms
}
