package bsuir.dc.rest.service

import bsuir.dc.rest.dto.from.PostRequestTo
import bsuir.dc.rest.dto.to.PostResponseTo
import bsuir.dc.rest.mapper.toEntity
import bsuir.dc.rest.mapper.toResponse
import bsuir.dc.rest.repository.IssueRepository
import bsuir.dc.rest.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val issueRepository: IssueRepository,
) {
    fun createPost(postRequestTo: PostRequestTo): PostResponseTo {
        val issue = issueRepository.findById(postRequestTo.issueId).orElseThrow { NoSuchElementException() }
        val post = postRequestTo.toEntity(issue)
        val savedPost = postRepository.save(post)
        return savedPost.toResponse()
    }

    fun getPostById(id: Long): PostResponseTo {
        val post = postRepository.findById(id).orElseThrow { NoSuchElementException() }
        return post.toResponse()
    }

    fun getAllPosts(): List<PostResponseTo> =
        postRepository.findAll().map { it.toResponse() }

    fun updatePost(id: Long, postRequestTo: PostRequestTo): PostResponseTo {
        val issue = issueRepository.findById(postRequestTo.issueId).orElseThrow { NoSuchElementException() }
        val updatedPost = postRequestTo.toEntity(issue).apply { this.id = id }
        return postRepository.save(updatedPost).toResponse()
    }

    fun deletePost(id: Long) {
        postRepository.findById(id).orElseThrow { NoSuchElementException() }
        postRepository.deleteById(id)
    }

    fun getPostsByIssueId(issueId: Long): List<PostResponseTo> {
        val issue = issueRepository.findById(issueId).orElseThrow { NoSuchElementException() }
        return issue.posts.map { it.toResponse() }
    }
}