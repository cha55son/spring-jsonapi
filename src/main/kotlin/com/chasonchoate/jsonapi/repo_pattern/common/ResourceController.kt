package com.chasonchoate.jsonapi.repo_pattern.common

import com.chasonchoate.jsonapi.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpServletRequest

open class ResourceController<T : Resource> {
    // GET /foos
    open fun index(): List<T> = throw NotImplementedError()
    // GET /foos/:id
    open fun show(id: String): T = throw NotImplementedError()
    // GET /foos/:id/relationships/bars
    open fun showRelationship(id: String, relationship: String): List<JSONAPIResourceID> = throw NotImplementedError()


    @GetMapping
    fun indexInternal(req: HttpServletRequest): JSONAPIResourcesDocument {
        val resources = index().map { it.toResource(req) }
        val doc = JSONAPIResourcesDocument(resources)
        doc.links["self"] = req.requestURL.toString()
        return doc
    }
    @GetMapping("/{id}")
    fun showInternal(@PathVariable("id") id: String, req: HttpServletRequest): JSONAPIResourceDocument {
        val resource = show(id).toResource(req)
        val doc = JSONAPIResourceDocument(resource)
        doc.links = resource.links
        resource.links.clear()
        return doc
    }
    @GetMapping("/{id}/relationships/{relation}")
    fun showRelationshipInternal(
            @PathVariable("id") id: String,
            @PathVariable("relation") relation: String,
            req: HttpServletRequest
    ): JSONAPIDocumentBase {
        // TODO: Don't pass along if the relation doesn't exist
        val resIds = showRelationship(id, relation)
        // TODO: Differentiate between has_one and has_many
        val doc = JSONAPIResourceIDsDocument(data = resIds)
        // TODO: Move into convenience method
//        val root = req.requestURL.toString().split(req.servletPath).first()
//        return "$root/${route.trim('/')}/$id"
        doc.links["self"] = req.requestURL.toString()
        doc.links["related"] = "blargh"
        return doc
    }


    // fun create() { ... }   // POST      /foos
    // fun update() { ... }   // PUT|PATCH /foos/:id
    // fun destroy() { ... }  // DELETE    /foos/:id

    // fun showRelationship() { ... }      // GET       /foos/:id/relationships/bars
    // fun createRelationship() { ... }    // POST      /foos/:id/relationships/bars
    // fun updateRelationship() { ... }    // PUT|PATCH /foos/:id/relationships/bars
    // fun destroyRelationship() { ... }   // DELETE    /foos/:id/relationships/bars

    // fun indexRelatedResources() { ... } // GET /bars/:id/bazs
    // fun showRelatedResource() { ... }   // GET /bars/:id/baz
}
