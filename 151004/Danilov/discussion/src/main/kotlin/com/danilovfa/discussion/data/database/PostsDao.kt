package com.danilovfa.discussion.data.database

import com.danilovfa.discussion.data.model.PostEntity

interface PostsDao {
    suspend fun create(post: PostEntity): Long?
    suspend fun get(id: Long): PostEntity?
    suspend fun getAll(): List<PostEntity>
    suspend fun update(id: Long, post: PostEntity): Boolean
    suspend fun delete(id: Long): Boolean
}