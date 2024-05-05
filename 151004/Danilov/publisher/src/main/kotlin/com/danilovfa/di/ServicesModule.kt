package com.danilovfa.di

import com.danilovfa.service.StickerService
import com.danilovfa.service.StoryService
import com.danilovfa.service.UserService
import org.koin.dsl.module

val servicesModule = module {
    single { StickerService(dao = get()) }
    single { StoryService(dao = get()) }
    single { UserService(dao = get()) }
}