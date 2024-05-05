package com.danilovfa.discussion.di

import com.danilovfa.discussion.data.database.DatabaseFactory
import com.danilovfa.discussion.data.database.PostDatabase
import com.danilovfa.discussion.data.database.PostsDao
import com.danilovfa.discussion.data.repository.PostsRepository
import com.danilovfa.discussion.data.repository.PostsRepositoryImpl
import com.datastax.driver.core.Session
import org.koin.dsl.module

val dataModule = module {
    single<Session> {
        DatabaseFactory.createAndConnect()
    }

    single<PostsDao> {
        PostDatabase(session = get())
    }

    single<PostsRepository> {
        PostsRepositoryImpl(dao = get())
    }
}