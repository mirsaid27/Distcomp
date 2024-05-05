package com.danilovfa.discussion.data.repository

import com.danilovfa.discussion.data.model.PostEntity
import com.danilovfa.discussion.model.post.Post

interface PostsRepository {
    suspend fun create(storyId: Long, content: String): Long?
    suspend fun get(id: Long): Post?
    suspend fun getAll(): List<Post>
    suspend fun update(id: Long, post: Post): Post?
    suspend fun delete(id: Long): Boolean
}