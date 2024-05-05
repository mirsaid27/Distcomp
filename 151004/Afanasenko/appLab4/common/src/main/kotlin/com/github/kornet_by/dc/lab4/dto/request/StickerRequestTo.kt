package com.github.kornet_by.dc.lab4.dto.request

import com.github.kornet_by.dc.lab4.bean.Sticker
import kotlinx.serialization.Serializable

@Serializable
data class StickerRequestTo(
	private val name: String
) {
	fun toBean(id: Long?): Sticker = Sticker(
		id, name
	)

	init {
		require(name.length in 2..32)
	}
}