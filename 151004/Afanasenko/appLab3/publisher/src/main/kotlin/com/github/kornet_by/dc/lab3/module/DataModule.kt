package com.github.kornet_by.dc.lab3.module

import com.github.kornet_by.dc.lab3.dao.AuthorDao
import com.github.kornet_by.dc.lab3.dao.IssueDao
import com.github.kornet_by.dc.lab3.dao.StickerDao
import com.github.kornet_by.dc.lab3.dao.impl.AuthorDaoImpl
import com.github.kornet_by.dc.lab3.dao.impl.IssueDaoImpl
import com.github.kornet_by.dc.lab3.dao.impl.StickerDaoImpl
import com.github.kornet_by.dc.lab3.repository.AuthorsRepository
import com.github.kornet_by.dc.lab3.repository.IssuesRepository
import com.github.kornet_by.dc.lab3.repository.StickersRepository
import com.github.kornet_by.dc.lab3.repository.impl.AuthorsRepositoryImpl
import com.github.kornet_by.dc.lab3.repository.impl.IssuesRepositoryImpl
import com.github.kornet_by.dc.lab3.repository.impl.StickersRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import java.sql.Connection

val authorsRepositoryQualifier: StringQualifier = StringQualifier("authors_repository")
val issuesRepositoryQualifier: StringQualifier = StringQualifier("issues_repository")
val stickersRepositoryQualifier: StringQualifier = StringQualifier("stickers_repository")

val dataModule: Module = module {
	single<AuthorDao> {
		val dbConnection = get<Connection>()

		AuthorDaoImpl(dbConnection)
	}
	single<IssueDao> {
		val dbConnection = get<Connection>()

		IssueDaoImpl(dbConnection)
	}
	single<StickerDao> {
		val dbConnection = get<Connection>()

		StickerDaoImpl(dbConnection)
	}

	single<AuthorsRepository>(authorsRepositoryQualifier) {
		val dao = get<AuthorDao>()

		AuthorsRepositoryImpl(dao)
	}
	single<IssuesRepository>(issuesRepositoryQualifier) {
		val dao = get<IssueDao>()

		IssuesRepositoryImpl(dao)
	}
	single<StickersRepository>(stickersRepositoryQualifier) {
		val dao = get<StickerDao>()

		StickersRepositoryImpl(dao)
	}
}