package com.danilovfa.util.exception

import  com.danilovfa.model.ErrorResponse
import com.danilovfa.util.exception.exceptions.ForbiddenException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

val Exception.httpCode: HttpStatusCode
    get() =
        when (this) {
            is IllegalArgumentException -> HttpStatusCode.BadRequest
            is NotFoundException -> HttpStatusCode.NotFound
            is ForbiddenException -> HttpStatusCode.Forbidden
            else -> throw this
        }

val Exception.httpResponse
    get() =
        ErrorResponse(
            errorCode = httpCode.value,
            errorMessage = message ?: "Error"
        )

suspend fun ApplicationCall.respondError(exception: Exception) {
    respond(exception.httpCode, exception.httpResponse)
}