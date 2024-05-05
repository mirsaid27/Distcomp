package com.danilovfa.discussion.data.database

import com.danilovfa.discussion.data.model.PostEntity
import com.datastax.driver.core.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class PostDatabase(private val session: Session) : PostsDao {
    companion object {
        const val TABLE_NAME = "distcomp.tbl_post"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_STORY_ID = "story_id"
        const val COLUMN_ID = "id"
        const val COLUMN_CONTENT = "content"

        private const val SELECT_POSTS =
            "SELECT $COLUMN_COUNTRY, $COLUMN_STORY_ID, $COLUMN_ID, $COLUMN_CONTENT FROM $TABLE_NAME;"

        /* Update exists post by id */
        private const val UPDATE_POST =
            "UPDATE $TABLE_NAME SET $COLUMN_COUNTRY, $COLUMN_STORY_ID = ?, $COLUMN_ID, $COLUMN_CONTENT = ? WHERE $COLUMN_ID = ?;"

        /* Delete post from table */
        private const val DELETE_POST = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?;"
    }

    override suspend fun create(post: PostEntity): Long? = withContext(Dispatchers.IO) {
        val id = if (post.id == -1L) Clock.System.now().toEpochMilliseconds() else post.id
        session.execute(
            "INSERT INTO distcomp.tbl_post (country, story_id, id, content) " + "VALUES ('${post.country}', ${post.storyId}, $id, '${post.content}');"
        )

        val rs = session.execute(SELECT_POSTS)
        val generatedKeys = rs.all()
        return@withContext if (generatedKeys.isNotEmpty()) {
            generatedKeys.maxByOrNull { it.getLong(COLUMN_ID) }?.getLong(COLUMN_ID) ?: 1
        } else {
            null
        }
    }

    override suspend fun get(id: Long): PostEntity? = withContext(Dispatchers.IO) {
        val all = getAll()
        return@withContext if (all.isNotEmpty()) {
            all.last { it.id == id }
        } else {
            null
        }
    }

    override suspend fun getAll(): List<PostEntity> = withContext(Dispatchers.IO) {
        val result = mutableListOf<PostEntity>()

        val resultSet = session.execute(SELECT_POSTS)
        for (row in resultSet.all()) {
            val id = row.getLong(COLUMN_ID)
            val storyId = row.getLong(COLUMN_STORY_ID)
            val content = row.getString(COLUMN_CONTENT)
            val country = row.getString(COLUMN_COUNTRY)
            result.add(
                PostEntity(
                    id = id, storyId = storyId, content = content, country = country
                )
            )
        }

        result
    }

    override suspend fun update(id: Long, post: PostEntity) = withContext(Dispatchers.IO) {
        return@withContext try {
//            delete(id)
//            create(post.copy(id = id))
//            session.execute(
//                "UPDATE $TABLE_NAME SET $COLUMN_CONTENT = '${post.content}' WHERE $COLUMN_ID = $id AND $COLUMN_COUNTRY = '${post.country}' AND $COLUMN_STORY_ID = ${post.storyId};"
//            )
            delete(id)
            create(post.copy(id = id))

            true
        } catch (e: Exception) {
            println("PostException: ${e.stackTraceToString()}")
            false
        }
    }

    override suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        return@withContext try {
            val entity = get(id)
            if (entity != null) {
                session.execute(
                    "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = $id AND $COLUMN_COUNTRY = '${entity.country}' AND $COLUMN_STORY_ID = ${entity.storyId};"
                )
            }

            true
        } catch (e: Exception) {
            println("PostException: ${e.stackTraceToString()}")
            false
        }
    }
}