package com.danilovfa.di

import com.danilovfa.config.getHttpClient
import io.ktor.client.*
import org.koin.dsl.module

val coreModule = module {
    single<HttpClient> {
        getHttpClient()
    }
}