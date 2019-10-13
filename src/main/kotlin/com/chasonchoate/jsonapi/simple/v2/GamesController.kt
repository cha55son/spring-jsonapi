package com.chasonchoate.jsonapi.simple.v2

import com.chasonchoate.jsonapi.JSONAPIResourceDocument
import com.chasonchoate.jsonapi.JSONAPIResourcesDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/v2/games")
class GamesController {
    @Autowired
    lateinit var repo: GamesRepository
    @Autowired
    lateinit var serializer: GamesSerializer

    @GetMapping
    fun index(): JSONAPIResourcesDocument {
        val games = repo.findAll()
        games.forEach {
            it.addLink("self", linkTo(methodOn(GamesController::class.java).show(it.id)).toString())
        }
        return serializer.serialize(games)
    }
//    @PostMapping
//    fun create(): JSONAPIResourceDocument = repo.createOne

    @GetMapping("{id}")
    fun show(@PathVariable("id") id: String): JSONAPIResourceDocument {
        val game = repo.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        game.addLink("self", linkTo(methodOn(GamesController::class.java).show(game.id)).toString())
        return serializer.serialize(game)
    }
//    @PatchMapping("{id}")
//    fun replace(@PathVariable("id") id: String): JSONAPIResourceDocument = repo.updateOne(id)
//    @DeleteMapping
//    fun delete(@PathVariable("id") id: String): JSONAPIResourceDocument = repo.deleteOne(id)

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
}
