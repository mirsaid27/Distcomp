package com.danilovfa.model.sticker

import kotlinx.serialization.Serializable

@Serializable
data class StickerCreateRequestTo(
    val name: String
)
