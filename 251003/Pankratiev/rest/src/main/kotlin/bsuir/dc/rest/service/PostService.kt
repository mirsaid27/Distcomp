package bsuir.dc.rest.service

import bsuir.dc.rest.dto.from.PostRequestTo
import bsuir.dc.rest.dto.to.PostResponseTo
import bsuir.dc.rest.mapper.toEntity
import bsuir.dc.rest.mapper.toResponse
import bsuir.dc.rest.repository.memory.PostInMemoryRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostInMemoryRepository,
) {
    fun createPost(postRequestTo: PostRequestTo): PostResponseTo {
        val post = postRequestTo.toEntity()
        val savedPost = postRepository.save(post)
        return savedPost.toResponse()
    }

    fun getPostById(id: Long): PostResponseTo {
        val post = postRepository.findById(id)
        return post.toResponse()
    }

    fun getAllPosts(): List<PostResponseTo> =
        postRepository.findAll().map { it.toResponse() }

    fun updatePost(id: Long, postRequestTo: PostRequestTo): PostResponseTo {
        val updatedPost = postRequestTo.toEntity().apply { this.id = id }
        return postRepository.update(updatedPost).toResponse()
    }

    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }

    fun getPostsByIssueId(issueId: Long): List<PostResponseTo> {
        return postRepository.findAll()
            .filter { it.issueId == issueId }
            .map { it.toResponse() }
    }
}