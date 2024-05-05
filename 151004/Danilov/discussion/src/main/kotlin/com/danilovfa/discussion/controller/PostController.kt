package com.danilovfa.discussion.controller

import com.danilovfa.discussion.model.post.PostCreateRequestTo
import com.danilovfa.discussion.model.post.PostUpdateRequestTo
import com.danilovfa.discussion.model.post.toPost
import com.danilovfa.discussion.model.post.toResponse
import com.danilovfa.discussion.service.PostService
import com.danilovfa.discussion.util.exception.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.postController(service: PostService) {
    post("/posts") {
        try {
            val request = call.receive<PostCreateRequestTo>()
            println("TEST_DEBUG, create post ${request.storyId}, ${request.content}")
            val post = service.createPost(request.storyId, request.content).toResponse()
            call.respond(HttpStatusCode.Created, post)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/posts/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            println("TEST_DEBUG, get post by Id $id")
            val response = service.getPostById(id).toResponse()
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/posts") {
        try {
            println("TEST_DEBUG, get all posts")
            val response = service.getPosts().map { it.toResponse() }
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    put("/posts") {
        try {
            val request = call.receive<PostUpdateRequestTo>()
            println("TEST_DEBUG, update post $request")
            val response = service.updatePost(request.id, request.toPost()).toResponse()
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    delete("/posts/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            println("TEST_DEBUG, delete post $id")
            service.deletePost(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }
}