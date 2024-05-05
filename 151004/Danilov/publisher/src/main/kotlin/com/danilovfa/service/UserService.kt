package com.danilovfa.service

import com.danilovfa.database.tables.UsersDao
import com.danilovfa.model.user.User
import com.danilovfa.util.exception.exceptions.ForbiddenException
import io.ktor.server.plugins.*
import kotlin.jvm.Throws

class UserService(
    private val dao: UsersDao
) {
    @Throws(NotFoundException::class, IllegalArgumentException::class, ForbiddenException::class)
    suspend fun createUser(login: String, password: String, firstName: String, lastName: String): User {
        if (login.length !in 2..64 || password.length !in 8..128 || firstName.length !in 2..64 || lastName.length !in 2..64) {
            throw IllegalArgumentException("Wrong arguments")
        }

        if (dao.userExists(login)) throw ForbiddenException("User already exists")

        val id = dao.insertUser(login, password, firstName, lastName)
        return getUserById(id)
    }

    suspend fun getUsers(): List<User> = dao.getUsers()

    @Throws(IllegalArgumentException::class, NotFoundException::class)
    suspend fun getUserById(id: Long?): User {
        if (id == null) throw IllegalArgumentException("Invalid user id")
        return dao.getUser(id) ?: throw NotFoundException("User was not found")
    }

    @Throws(IllegalArgumentException::class, NotFoundException::class)
    suspend fun updateUser(id: Long, login: String, password: String, firstName: String, lastName: String): User {
        if (login.length !in 2..64 || password.length !in 8..128 ||
            firstName.length !in 2..64 || lastName.length !in 2..64) {
            throw IllegalArgumentException("Wrong arguments")
        }

        return dao.updateUser(id, login, password, firstName, lastName) ?: throw NotFoundException("User was not found")
    }

    @Throws(IllegalArgumentException::class, NotFoundException::class)
    suspend fun deleteUser(id: Long?) {
        if (id == null) throw IllegalArgumentException("Wrong user id")
        if (dao.deleteUser(id).not()) throw NotFoundException("User not found")
    }
}

