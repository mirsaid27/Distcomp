package com.github.kornet_by.dc.lab3.dto.request

import com.github.kornet_by.dc.lab3.bean.Sticker
import kotlinx.serialization.Serializable

@Serializable
data class StickerRequestToId(
	private val id: Long, private val name: String
) {
	fun toBean(): Sticker = Sticker(
		id, name
	)

	init {
		require(name.length in 2..32)
	}
}