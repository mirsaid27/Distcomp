package com.danilovfa.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostResponseTo(
    val id: Long,
    val storyId: Long,
    val content: String
)

fun Post.toResponse() =
    PostResponseTo(
        id = id,
        storyId = storyId,
        content = content
    )
