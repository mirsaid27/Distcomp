package com.danilovfa.model.user

data class User(
    val id: Long,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
