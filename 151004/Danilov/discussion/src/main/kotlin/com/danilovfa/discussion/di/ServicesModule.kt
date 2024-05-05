package com.danilovfa.discussion.di

import com.danilovfa.discussion.service.PostService
import org.koin.dsl.module

val servicesModule = module {
    single { PostService(repository = get()) }
}