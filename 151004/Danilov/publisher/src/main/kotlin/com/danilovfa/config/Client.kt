package com.danilovfa.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun getHttpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}