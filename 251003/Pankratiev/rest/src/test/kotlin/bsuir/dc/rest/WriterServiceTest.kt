package bsuir.dc.rest

import bsuir.dc.rest.dto.from.WriterRequestTo
import bsuir.dc.rest.entity.Issue
import bsuir.dc.rest.repository.memory.IssueInMemoryRepository
import bsuir.dc.rest.service.WriterService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WriterServiceTest {

    @Autowired
    private lateinit var writerService: WriterService

    @Autowired
    private lateinit var issueRepository: IssueInMemoryRepository

    @Test
    fun createWriterTest() {
        val writerRequestTo = WriterRequestTo(login = "user1", password = "pass", firstname = "First", lastname = "Last")
        val createdWriter = writerService.createWriter(writerRequestTo)
        assertNotNull(createdWriter.id)
        assertEquals("user1", createdWriter.login)
    }

    @Test
    fun getWriterByIdTest() {
        val writerRequestTo = WriterRequestTo(login = "user2", password = "pass", firstname = "First2", lastname = "Last2")
        val createdWriter = writerService.createWriter(writerRequestTo)
        val foundWriter = writerService.getWriterById(createdWriter.id)
        assertEquals(createdWriter.id, foundWriter.id)
        assertEquals("user2", foundWriter.login)
    }

    @Test
    fun getAllWritersTest() {
        writerService.createWriter(WriterRequestTo(login = "user3", password = "pass", firstname = "First3", lastname = "Last3"))
        writerService.createWriter(WriterRequestTo(login = "user4", password = "pass", firstname = "First4", lastname = "Last4"))
        val writers = writerService.getAllWriters()
        assertTrue(writers.any { it.login == "user3" })
        assertTrue(writers.any { it.login == "user4" })
    }

    @Test
    fun updateWriterTest() {
        val writerRequestTo = WriterRequestTo(login = "user5", password = "pass", firstname = "First5", lastname = "Last5")
        val createdWriter = writerService.createWriter(writerRequestTo)
        val updatedWriterRequestTo = WriterRequestTo(login = "updatedUser5", password = "newpass", firstname = "NewFirst5", lastname = "NewLast5")
        val updatedWriter = writerService.updateWriter(createdWriter.id, updatedWriterRequestTo)
        assertEquals(createdWriter.id, updatedWriter.id)
        assertEquals("updatedUser5", updatedWriter.login)
    }

    @Test
    fun deleteWriterTest() {
        val writerRequestTo = WriterRequestTo(login = "user6", password = "pass", firstname = "First6", lastname = "Last6")
        val createdWriter = writerService.createWriter(writerRequestTo)
        writerService.deleteWriter(createdWriter.id)
        assertThrows<NoSuchElementException> {
            writerService.getWriterById(createdWriter.id)
        }
    }

    @Test
    fun getWriterByIssueIdTest() {
        val writerRequestTo = WriterRequestTo(login = "user7", password = "pass", firstname = "First7", lastname = "Last7")
        val createdWriter = writerService.createWriter(writerRequestTo)
        val issue = Issue(
            id = 1,
            title = "Issue",
            content = "Content",
            writerId = createdWriter.id,
        )
        issueRepository.save(issue)
        val foundWriter = writerService.getWriterByIssueId(issue.id)
        assertEquals(createdWriter.id, foundWriter.id)
        assertEquals("user7", foundWriter.login)
    }
}
