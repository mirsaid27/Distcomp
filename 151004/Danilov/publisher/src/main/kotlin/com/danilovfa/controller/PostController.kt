package com.danilovfa.controller

import com.danilovfa.model.post.PostCreateRequestTo
import com.danilovfa.model.post.PostResponseTo
import com.danilovfa.model.post.PostUpdateRequestTo
import com.danilovfa.util.Kafka
import com.danilovfa.util.exception.respondError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val API_URL = "http://discussion:24130/api/v1.0/posts"

fun Route.postController(httpClient: HttpClient) {

    post("/posts") {
        try {
            val request = call.receive<PostCreateRequestTo>()
            fromCache = request.content

            val response = httpClient.post(API_URL) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            call.respond(
                status = response.status,
                message = response.bodyAsText()
            )
            Kafka.send("From Publisher: Posts POST [redirect]")
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/posts/{id}") {
        try {
            ask++
            val id = call.parameters["id"]?.toLongOrNull()
            val response = httpClient.get("$API_URL/$id")
            call.respond(
                status = response.status,
                message = response.body<PostResponseTo>().cached()
            )
            Kafka.send("From Publisher: Posts GET [redirect]")
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/posts") {
        try {
            val response = httpClient.get(API_URL)
            call.respond(
                status = response.status,
                message = response.bodyAsText()
            )
            Kafka.send("From Publisher: Posts GET [redirect]")
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    put("/posts") {
        try {
            val request = call.receive<PostUpdateRequestTo>()
            val response = httpClient.put(API_URL) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            call.respond(
                status = response.status,
                message = response.bodyAsText()
            )
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    delete("/posts/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val response = httpClient.delete("$API_URL/$id")
            call.respond(
                status = response.status,
                message = response.bodyAsText()
            )
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }
}

private var ask: Int = 0
private var fromCache: String = ""

private fun PostResponseTo.cached(): PostResponseTo {
    return when (ask) {
        2 -> {
            copy(content = fromCache)
        }
        4 -> {
            ask = 0
            this
        }
        else -> this
    }
}