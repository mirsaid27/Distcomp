package com.danilovfa.model.sticker

import kotlinx.serialization.Serializable

@Serializable
data class StickerUpdateRequestTo(
    val id: Long,
    val name: String
)
