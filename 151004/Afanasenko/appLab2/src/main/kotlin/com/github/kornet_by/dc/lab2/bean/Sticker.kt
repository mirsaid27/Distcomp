package com.github.kornet_by.dc.lab2.bean

import com.github.kornet_by.dc.lab2.dto.response.StickerResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
	val id: Long?, val name: String
) {
	fun toResponse(): StickerResponseTo = StickerResponseTo(id, name)
}