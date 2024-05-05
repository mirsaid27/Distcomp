package com.danilovfa.database.tables

import com.danilovfa.database.DatabaseFactory.dbExec
import com.danilovfa.model.sticker.Sticker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object StickersTable : Table(), StickersDao {
    override val tableName: String = "tbl_sticker"

    val id = long("id").autoIncrement()
    val name = text("name")
    override val primaryKey = PrimaryKey(id)

    override suspend fun getStickers(): List<Sticker> = dbExec {
        selectAll().map { it.toSticker() }
    }

    override suspend fun getSticker(id: Long): Sticker? = dbExec {
        select { StickersTable.id eq id }
            .map { it.toSticker() }
            .singleOrNull()
    }

    override suspend fun insertSticker(name: String): Long = dbExec {
        insert {
            it[StickersTable.name] = name
        } get id
    }

    override suspend fun updateSticker(id: Long, name: String): Sticker? {
        dbExec {
            update(
                where = { StickersTable.id eq id }
            ) {
                it[StickersTable.name] = name
            }
        }

        return getSticker(id)
    }

    override suspend fun deleteSticker(id: Long): Boolean = dbExec {
        deleteWhere { StickersTable.id eq id } > 0
    }
}

private fun ResultRow.toSticker() =
    Sticker(
        id = this[StickersTable.id],
        name = this[StickersTable.name],
    )

interface StickersDao {
    suspend fun getStickers(): List<Sticker>
    suspend fun getSticker(id: Long): Sticker?
    suspend fun insertSticker(name: String): Long
    suspend fun updateSticker(id: Long, name: String): Sticker?
    suspend fun deleteSticker(id: Long): Boolean
}