package com.danilovfa.service

import com.danilovfa.database.tables.StoriesDao
import com.danilovfa.model.story.Story
import com.danilovfa.util.exception.exceptions.ForbiddenException
import io.ktor.server.plugins.*
import kotlin.jvm.Throws

class StoryService(
    private val dao: StoriesDao
) {
    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun createStory(userId: Long, title: String, content: String): Story {
        if (title.length !in 2..64 || content.length !in 4..2048) throw IllegalArgumentException("Wrong argument")
        if (dao.storyExists(title)) throw ForbiddenException("Story with same title already exists")
        if (dao.canCreate(userId).not()) throw ForbiddenException("User does not exists")
        val id = dao.insertStory(userId, title, content)
        return getStoryById(id)
    }

    suspend fun getStories(): List<Story> = dao.getStories()

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun getStoryById(id: Long?): Story {
        if (id == null) throw IllegalArgumentException("Wring story id")
        return dao.getStory(id) ?: throw NotFoundException("Story not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun updateStory(id: Long, userId: Long, title: String, content: String): Story {
        if (title.length !in 2..64 || content.length !in 4..2048) throw IllegalArgumentException("Wrong argument")
        return dao.updateStory(id, userId, title, content) ?: throw NotFoundException("Story not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun deleteStory(id: Long?) {
        if (id == null) throw IllegalArgumentException("Wrong story id")
        if (dao.deleteStory(id).not()) throw NotFoundException("Story not found")
    }
}