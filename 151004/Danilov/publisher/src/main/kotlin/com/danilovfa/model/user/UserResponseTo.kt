package com.danilovfa.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseTo(
    val id: Long,
    val login: String,
    val password: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName: String
)

fun User.toResponse() =
    UserResponseTo(
        id = id,
        login = login,
        password = password,
        firstName = firstName,
        lastName = lastName
    )
