package com.danilovfa.discussion.model.post


data class Post(
    val id: Long,
    val country: String,
    val storyId: Long,
    val content: String
)
