package com.danilovfa.discussion

import com.danilovfa.discussion.config.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val port = System.getenv("SERVER_PORT")?.toInt() ?: 24130
    val host = System.getenv("SERVER_HOST") ?: "0.0.0.0"

    embeddedServer(Netty, port = port, host = host, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDI()
    configureSerialization()
    configureHTTP()
    configureRouting()
//    configureKafka()
}