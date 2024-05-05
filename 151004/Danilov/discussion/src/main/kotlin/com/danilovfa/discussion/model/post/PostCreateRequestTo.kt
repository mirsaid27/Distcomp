package com.danilovfa.discussion.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostCreateRequestTo(
    val storyId: Long,
    val content: String
)