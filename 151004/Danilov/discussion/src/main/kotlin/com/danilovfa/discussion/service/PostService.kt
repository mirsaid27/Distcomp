package com.danilovfa.discussion.service

import com.danilovfa.discussion.data.repository.PostsRepository
import com.danilovfa.discussion.model.post.Post
import com.danilovfa.discussion.util.exception.exceptions.ForbiddenException
import io.ktor.server.plugins.*
import kotlin.jvm.Throws

class PostService(
    private val repository: PostsRepository
) {

    @Throws(IllegalArgumentException::class, NotFoundException::class, ForbiddenException::class)
    suspend fun createPost(storyId: Long, content: String): Post {
        if (content.length !in 2..2048) throw IllegalArgumentException("Wrong content size (not 2..2048)")
        val id = repository.create(storyId, content)
        return getPostById(id)
    }

    suspend fun getPosts(): List<Post> =
        repository.getAll()

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun getPostById(id: Long?): Post {
        if (id == null) throw IllegalArgumentException("Wrong post id")
        return repository.get(id) ?: throw NotFoundException("Post not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun updatePost(id: Long, post: Post): Post {
        if (post.content.length !in 2..2048) throw IllegalArgumentException("Wrong content size (not 2..2048)")
        return repository.update(id, post) ?: throw NotFoundException("Post not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun deletePost(id: Long?) {
        if (id == null) throw IllegalArgumentException("Wrong post id")
        if (repository.delete(id).not()) throw NotFoundException("Post not found")
    }
}