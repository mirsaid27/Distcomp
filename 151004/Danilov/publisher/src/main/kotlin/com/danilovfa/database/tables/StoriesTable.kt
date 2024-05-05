package com.danilovfa.database.tables

import com.danilovfa.database.DatabaseFactory.dbExec
import com.danilovfa.model.story.Story
import com.danilovfa.util.currentTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object StoriesTable : Table(), StoriesDao {
    override val tableName: String = "tbl_story"

    val id = long("id").autoIncrement()
    val userId = long("user_id")
    val title = text("title")
    val content = text("content")
    val created = long("created")
    val modified = long("modified")
    override val primaryKey = PrimaryKey(id)

    override suspend fun getStories(): List<Story> = dbExec {
        selectAll().map { it.toStory() }
    }

    override suspend fun getStory(id: Long): Story? = dbExec {
        select { StoriesTable.id eq id }
            .map { it.toStory() }
            .singleOrNull()
    }

    override suspend fun insertStory(
        userId: Long,
        title: String,
        content: String
    ): Long = dbExec {
        insert {
            it[StoriesTable.userId] = userId
            it[StoriesTable.title] = title
            it[StoriesTable.content] = content
            it[StoriesTable.created] = currentTime()
            it[StoriesTable.modified] = currentTime()
        } get id
    }

    override suspend fun updateStory(
        id: Long,
        userId: Long,
        title: String,
        content: String,
    ): Story? {
        dbExec {
            update(
                where = { StoriesTable.id eq id }
            ) {
                it[StoriesTable.userId] = userId
                it[StoriesTable.title] = title
                it[StoriesTable.content] = content
                it[StoriesTable.modified] = currentTime()
            }
        }

        return StoriesTable.getStory(id)
    }

    override suspend fun deleteStory(id: Long): Boolean = dbExec {
        deleteWhere { StoriesTable.id eq id } > 0
    }

    override suspend fun storyExists(title: String): Boolean = dbExec {
        select { StoriesTable.title eq title }
            .map { it.toStory() }
            .singleOrNull() != null
    }

    override suspend fun storyExists(id: Long): Boolean = dbExec {
        select { StoriesTable.id eq id }
            .map { it.toStory() }
            .singleOrNull() != null
    }

    override suspend fun canCreate(userId: Long): Boolean =
        UsersTable.userExists(userId)
}

private fun ResultRow.toStory() =
    Story(
        id = this[StoriesTable.id],
        userId = this[StoriesTable.userId],
        title = this[StoriesTable.title],
        content = this[StoriesTable.content],
        created = this[StoriesTable.created],
        modified = this[StoriesTable.modified]
    )

interface StoriesDao {
    suspend fun getStories(): List<Story>
    suspend fun getStory(id: Long): Story?
    suspend fun insertStory(userId: Long, title: String, content: String): Long
    suspend fun updateStory(id: Long, userId: Long, title: String, content: String): Story?
    suspend fun deleteStory(id: Long): Boolean
    suspend fun storyExists(title: String): Boolean
    suspend fun storyExists(id: Long): Boolean
    suspend fun canCreate(userId: Long): Boolean
}