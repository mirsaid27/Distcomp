package com.danilovfa.model.story

import kotlinx.serialization.Serializable

@Serializable
data class StoryUpdateRequestTo(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String
)
