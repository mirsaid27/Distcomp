package com.danilovfa.discussion.controller

import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.apiController() {
    route("api/v1.0") {
        postController(service = get())
    }
}