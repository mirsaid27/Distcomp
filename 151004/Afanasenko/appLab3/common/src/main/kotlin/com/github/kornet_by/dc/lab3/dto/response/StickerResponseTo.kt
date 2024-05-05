package com.github.kornet_by.dc.lab3.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class StickerResponseTo(
	private val id: Long?, private val name: String
)