package com.chasonchoate.jsonapi.simple

import org.springframework.stereotype.Repository

// interface ResourceController<T> {
//     fun index(...): T      // GET       /foos
//     fun create() { ... }   // POST      /foos

//     fun show(...): T       // GET       /foos/:id
//     fun update() { ... }   // PUT|PATCH /foos/:id
//     fun destroy() { ... }  // DELETE    /foos/:id

//     fun showRelationship() { ... }      // GET       /foos/:id/relationships/bars
//     fun createRelationship() { ... }    // POST      /foos/:id/relationships/bars
//     fun updateRelationship() { ... }    // PUT|PATCH /foos/:id/relationships/bars
//     fun destroyRelationship() { ... }   // DELETE    /foos/:id/relationships/bars

//     fun indexRelatedResources() { ... } // GET /bars/:id/bazs
//     fun showRelatedResource() { ... }   // GET /bars/:id/baz
// }

// class ResourcesControllerBase<T>(resourceClass: Class<T>) : ResourceController<T> {
//     @GetMapping
//     fun index(): T = NotImplemented()
//     @PostMapping
//     fun create(): T = NotImplemented()

//     @GetMapping("/{id}")
//     fun show(): T = NotImplemented()
//     @RequestMapping("/{id}", methods = ["PUT", "PATCH"])
//     fun update() { ... }
//     @DeleteMapping("/{id}")
//     fun destroy() { ... }
//
//     @GetMapping("/{id}/relationships/{relation}")
//     fun showRelationship() { ... }
//     @PostMapping("/{id}/relationships/{relation}")
//     fun createRelationship() { ... }
//     @RequestMapping("/{id}/relationships/{relation}", methods = ["PUT", "PATCH"])
//     fun updateRelationship() { ... }
//     @DeleteMapping("/{id}/relationships/{relation}")
//     fun destroyRelationship() { ... }
//
//     @GetMapping("/{id}/{relation}")
//     fun indexRelatedResources() { ... }
// }

// @JSONAPIController("/cities")
// class CitiesController(City::class) : ResourceController<City> {
//     @GetMapping("/v2")
//     fun index(...): T {
//
//     }
// }

// jsonapi
//     .root { doc ->
//         doc.links["cities"] = link(method(CitiesController).index())
//         doc.links["favoriteLocations"] = link(method(FavoriteLocations).index())
//     }

/**
 *
 * ResourceController: Handles routing. Should be able to change routes easily and clients should still work.
 * ResourceRepository: Manages the retrieval of resources. Most importantly the including of related resources.
 *                     Should allow devs to easily change how resources are queried.
 *           Resource: Manages the access and serialization of the resource.
 */

