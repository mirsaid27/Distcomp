package com.github.kornet_by.dc.lab2.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponseTo(
	private val id: Long?, private val issueId: Long, private val content: String
)