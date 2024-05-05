package com.danilovfa.model.sticker

import kotlinx.serialization.Serializable

@Serializable
data class StickerResponseTo(
    val id: Long,
    val name: String
)

fun Sticker.toResponse() =
    StickerResponseTo(
        id = id,
        name = name
    )
