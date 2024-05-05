package com.danilovfa.model.story

data class Story(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val created: Long,
    val modified: Long
)
