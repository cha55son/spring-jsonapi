package com.chasonchoate.jsonapi.common

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpServletRequest

open class ResourceController<T : Resource> {
    // GET /foos
    open fun index(): List<T> = throw NotImplementedError()
    // GET /foos/:id
    open fun show(id: String): T = throw NotImplementedError()


    @GetMapping("/")
    fun indexInternal(req: HttpServletRequest): JSONAPIResourcesDocument {
        val resources = index().map { it.toResource(req) }
        val doc = JSONAPIResourcesDocument(resources)
        doc.links = mapOf("self" to req.requestURL.toString())
        return doc
    }
    @GetMapping("/{id}")
    fun showInternal(@PathVariable("id") id: String, req: HttpServletRequest): JSONAPIResourceDocument {
        val resource = show(id).toResource()
        val doc = JSONAPIResourceDocument(resource)
        doc.links = mapOf("self" to req.requestURL.toString())
        return doc
    }

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
