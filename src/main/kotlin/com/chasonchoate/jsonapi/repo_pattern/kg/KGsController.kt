package com.chasonchoate.jsonapi.repo_pattern.kg

import com.chasonchoate.jsonapi.repo_pattern.common.JSONAPIController
import com.chasonchoate.jsonapi.repo_pattern.common.ResourceController
import org.springframework.web.bind.annotation.RequestMapping

@JSONAPIController
@RequestMapping(KGS_PATH)
class KGsController : ResourceController<KG>() {
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
    // fun showRelationship() { ... }       // GET /cities/:id/relationships/comms
}
