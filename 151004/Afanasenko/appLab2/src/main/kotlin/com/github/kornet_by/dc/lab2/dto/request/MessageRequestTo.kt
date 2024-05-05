package com.github.kornet_by.dc.lab2.dto.request

import com.github.kornet_by.dc.lab2.bean.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageRequestTo(
	private val issueId: Long, private val content: String
) {
	fun toBean(id: Long?): Message = Message(
		id, issueId, content
	)

	init {
		require(content.length in 4..2048)
	}
}