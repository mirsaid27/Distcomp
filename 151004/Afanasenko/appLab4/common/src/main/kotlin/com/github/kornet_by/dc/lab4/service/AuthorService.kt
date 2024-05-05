package com.github.kornet_by.dc.lab4.service

import com.github.kornet_by.dc.lab4.dto.request.AuthorRequestTo
import com.github.kornet_by.dc.lab4.dto.request.AuthorRequestToId
import com.github.kornet_by.dc.lab4.dto.response.AuthorResponseTo

interface AuthorService {
	suspend fun create(requestTo: AuthorRequestTo?): AuthorResponseTo?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<AuthorResponseTo>

	suspend fun getById(id: Long): AuthorResponseTo?

	suspend fun update(requestTo: AuthorRequestToId?): AuthorResponseTo?
}