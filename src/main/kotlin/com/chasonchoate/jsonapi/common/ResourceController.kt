package com.chasonchoate.jsonapi.common

import org.springframework.web.bind.annotation.GetMapping

open class ResourceController<T : Resource> {
    @GetMapping
    fun indexInternal(): JSONAPIResourcesDocument {
        val resources = index().map { it.toResource() }
        return JSONAPIResourcesDocument(resources)
    }
    // GET /foos
    open fun index(): List<T> = throw NotImplementedError()
    // fun create() { ... }   // POST      /foos
    // fun show() { ... }     // GET       /foos/:id
    // fun update() { ... }   // PUT|PATCH /foos/:id
    // fun destroy() { ... }  // DELETE    /foos/:id

    // fun showRelationship() { ... }      // GET       /foos/:id/relationships/bars
    // fun createRelationship() { ... }    // POST      /foos/:id/relationships/bars
    // fun updateRelationship() { ... }    // PUT|PATCH /foos/:id/relationships/bars
    // fun destroyRelationship() { ... }   // DELETE    /foos/:id/relationships/bars

    // fun indexRelatedResources() { ... } // GET /bars/:id/bazs
    // fun showRelatedResource() { ... }   // GET /bars/:id/baz
}
