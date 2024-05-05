package com.danilovfa.controller

import com.danilovfa.model.sticker.StickerCreateRequestTo
import com.danilovfa.model.sticker.StickerUpdateRequestTo
import com.danilovfa.model.sticker.toResponse
import com.danilovfa.service.StickerService
import com.danilovfa.util.exception.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.stickerController(service: StickerService) {
    post("/stickers") {
        try {
            val request = call.receive<StickerCreateRequestTo>()
            val post = service.createSticker(request.name).toResponse()
            call.respond(HttpStatusCode.Created, post)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/stickers/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val post = service.getStickerById(id).toResponse()
            call.respond(HttpStatusCode.OK, post)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/stickers") {
        try {
            val response = service.getStickers().map { it.toResponse() }
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    put("/stickers") {
        try {
            val request = call.receive<StickerUpdateRequestTo>()
            val response = service.updateSticker(request.id, request.name).toResponse()
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    delete("/stickers/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            service.deleteSticker(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }
}