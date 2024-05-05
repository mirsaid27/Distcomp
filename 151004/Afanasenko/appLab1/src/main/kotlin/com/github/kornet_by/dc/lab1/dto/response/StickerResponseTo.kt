package com.github.kornet_by.dc.lab1.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class StickerResponseTo(
	private val id: Long, private val name: String
)