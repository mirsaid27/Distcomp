package com.danilovfa.model.story

import kotlinx.serialization.Serializable

@Serializable
data class StoryCreateRequestTo(
    val userId: Long,
    val title: String,
    val content: String
)
