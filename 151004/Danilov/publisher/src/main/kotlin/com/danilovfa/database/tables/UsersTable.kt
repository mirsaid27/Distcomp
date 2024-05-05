package com.danilovfa.database.tables

import com.danilovfa.database.DatabaseFactory.dbExec
import com.danilovfa.model.user.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object UsersTable : Table(), UsersDao {
    override val tableName: String = "tbl_user"

    val id = long("id").autoIncrement()
    val login = text("login")
    val password = text("password")
    val firstName = text("firstName")
    val lastName = text("lastName")
    override val primaryKey = PrimaryKey(id)

    override suspend fun getUsers(): List<User> = dbExec {
        selectAll().map { it.toUser() }
    }

    override suspend fun getUser(id: Long): User? = dbExec {
        select { UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun insertUser(login: String, password: String, firstName: String, lastName: String): Long =
        dbExec {
            insert {
                it[UsersTable.login] = login
                it[UsersTable.password] = password
                it[UsersTable.firstName] = firstName
                it[UsersTable.lastName] = lastName
            } get id
        }

    override suspend fun updateUser(
        id: Long,
        login: String,
        password: String,
        firstName: String,
        lastName: String
    ): User? = dbExec {
        update(
            where = { UsersTable.id eq id }
        ) {
            it[UsersTable.login] = login
            it[UsersTable.password] = password
            it[UsersTable.firstName] = firstName
            it[UsersTable.lastName] = lastName
        }
        select { UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun deleteUser(id: Long): Boolean = dbExec {
        deleteWhere { UsersTable.id eq id } > 0
    }

    override suspend fun userExists(login: String): Boolean = dbExec {
        select { UsersTable.login eq login }
            .map { it.toUser() }
            .singleOrNull() != null
    }

    override suspend fun userExists(id: Long): Boolean = dbExec {
        select { UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull() != null
    }
}

private fun ResultRow.toUser() =
    User(
        id = this[UsersTable.id],
        login = this[UsersTable.login],
        password = this[UsersTable.password],
        firstName = this[UsersTable.firstName],
        lastName = this[UsersTable.lastName]
    )

interface UsersDao {
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: Long): User?
    suspend fun insertUser(login: String, password: String, firstName: String, lastName: String): Long
    suspend fun updateUser(id: Long, login: String, password: String, firstName: String, lastName: String): User?
    suspend fun deleteUser(id: Long): Boolean
    suspend fun userExists(login: String): Boolean
    suspend fun userExists(id: Long): Boolean
}