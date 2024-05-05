package com.danilovfa.controller

import com.danilovfa.model.user.UserCreateRequestTo
import com.danilovfa.model.user.UserUpdateRequestTo
import com.danilovfa.model.user.toResponse
import com.danilovfa.service.UserService
import com.danilovfa.util.exception.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userController(service: UserService) {
    post("/users") {
        try {
            val request = call.receive<UserCreateRequestTo>()
            val user =
                service.createUser(
                    login = request.login,
                    password = request.password,
                    firstName = request.firstName,
                    lastName = request.lastName
                ).toResponse()
            call.respond(HttpStatusCode.Created, user)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/users/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val user = service.getUserById(id).toResponse()
            call.respond(HttpStatusCode.OK, user)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    get("/users") {
        val response = service.getUsers().map { it.toResponse() }
        call.respond(HttpStatusCode.OK, response)
    }

    put("/users") {
        try {
            val request = call.receive<UserUpdateRequestTo>()
            val response = service.updateUser(
                id = request.id,
                login = request.login,
                password = request.password,
                firstName = request.firstName,
                lastName = request.lastName
            ).toResponse()
            call.respond(HttpStatusCode.OK, response)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }

    delete("/users/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            service.deleteUser(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (exception: Exception) {
            call.respondError(exception)
        }
    }
}