package com.danilovfa.discussion.data.model

import com.danilovfa.discussion.model.post.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostEntity(
    val id: Long,
    val country: String,
    val storyId: Long,
    val content: String
)

fun PostEntity.toPost() = Post(
    id = id,
    country = country,
    storyId = storyId,
    content = content
)

fun Post.toEntity() = PostEntity(
    id = id,
    country = "country",
    storyId = storyId,
    content = content
)