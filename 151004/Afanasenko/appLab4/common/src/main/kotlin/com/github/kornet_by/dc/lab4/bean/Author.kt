package com.github.kornet_by.dc.lab4.bean

import com.github.kornet_by.dc.lab4.dto.response.AuthorResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Author(
	val id: Long?, val login: String, val password: String, val firstname: String, val lastname: String
) {
	fun toResponse(): AuthorResponseTo = AuthorResponseTo(id, login, password, firstname, lastname)
}