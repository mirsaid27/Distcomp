package com.danilovfa.model.story

import kotlinx.serialization.Serializable

@Serializable
data class StoryResponseTo(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val created: Long,
    val modified: Long
)

fun Story.toResponse() =
    StoryResponseTo(
        id = id,
        userId = userId,
        title = title,
        content = content,
        created = created,
        modified = modified
    )
