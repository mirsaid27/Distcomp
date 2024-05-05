package com.danilovfa.config

import com.danilovfa.di.koinModules
import io.ktor.server.application.*
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(koinModules)
    }
}