package com.danilovfa.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostUpdateRequestTo(
    val id: Long,
    val storyId: Long,
    val content: String
)