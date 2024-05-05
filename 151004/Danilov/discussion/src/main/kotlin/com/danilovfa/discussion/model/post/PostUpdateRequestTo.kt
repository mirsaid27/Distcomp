package com.danilovfa.discussion.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostUpdateRequestTo(
    val id: Long,
    val storyId: Long,
    val content: String
)

fun PostUpdateRequestTo.toPost() = Post(
    id = id,
    storyId = storyId,
    content = content,
    country = ""
)