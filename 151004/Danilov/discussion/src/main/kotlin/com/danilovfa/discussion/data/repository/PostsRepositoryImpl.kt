package com.danilovfa.discussion.data.repository

import com.danilovfa.discussion.data.database.PostsDao
import com.danilovfa.discussion.data.model.PostEntity
import com.danilovfa.discussion.data.model.toEntity
import com.danilovfa.discussion.data.model.toPost
import com.danilovfa.discussion.model.post.Post
import java.util.*

class PostsRepositoryImpl(
    private val dao: PostsDao
) : PostsRepository {
    override suspend fun create(storyId: Long, content: String): Long? {
        return dao.create(
            PostEntity(id = -1, country = UUID.randomUUID().toString(), storyId = storyId, content = content)
        )
    }

    override suspend fun get(id: Long): Post? =
        dao.get(id)?.toPost()

    override suspend fun getAll(): List<Post> =
        dao.getAll().map { it.toPost() }

    override suspend fun update(id: Long, post: Post): Post? {
        return if (dao.update(id, post.toEntity())) {
            get(id)
        } else null
    }

    override suspend fun delete(id: Long): Boolean =
        dao.delete(id)
}