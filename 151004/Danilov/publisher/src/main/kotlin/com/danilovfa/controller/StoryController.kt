package com.danilovfa.controller

import com.danilovfa.model.story.StoryCreateRequestTo
import com.danilovfa.model.story.StoryUpdateRequestTo
import com.danilovfa.model.story.toResponse
import com.danilovfa.service.StoryService
import com.danilovfa.util.exception.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storyController(service: StoryService) {
    post("/storys") {
        try {
            val request = call.receive<StoryCreateRequestTo>()
            val post = service.createStory(request.userId, request.title, request.content).toResponse()
            call.respond(HttpStatusCode.Created, post)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/storys/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val post = service.getStoryById(id).toResponse()
            call.respond(HttpStatusCode.OK, post)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/storys") {
        try {
            val response = service.getStories().map { it.toResponse() }
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    put("/storys") {
        try {
            val request = call.receive<StoryUpdateRequestTo>()
            val response = service.updateStory(request.id, request.userId, request.title, request.content)
                .toResponse()

            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    delete("/storys/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            service.deleteStory(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }
}