package bsuir.dc.rest

import bsuir.dc.rest.dto.from.IssueRequestTo
import bsuir.dc.rest.entity.IssueLabel
import bsuir.dc.rest.entity.Label
import bsuir.dc.rest.entity.Writer
import bsuir.dc.rest.repository.memory.IssueLabelInMemoryRepository
import bsuir.dc.rest.repository.memory.LabelInMemoryRepository
import bsuir.dc.rest.repository.memory.WriterInMemoryRepository
import bsuir.dc.rest.service.IssueService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IssueServiceTest {

	@Autowired
	private lateinit var issueService: IssueService

	@Autowired
	private lateinit var labelRepository: LabelInMemoryRepository

	@Autowired
	private lateinit var issueLabelRepository: IssueLabelInMemoryRepository

	@Autowired
	private lateinit var writerRepository: WriterInMemoryRepository

	@Test
	fun createIssueTest() {
		val issueRequestTo = IssueRequestTo(title = "Test Issue", content = "Test Content", writerId = 1)
		val createdIssue = issueService.createIssue(issueRequestTo)
		assertNotNull(createdIssue.id)
		assertEquals("Test Issue", createdIssue.title)
		assertEquals("Test Content", createdIssue.content)
	}

	@Test
	fun getIssueByIdTest() {
		val issueRequestTo = IssueRequestTo(title = "Find me", content = "Content", writerId = 1)
		val createdIssue = issueService.createIssue(issueRequestTo)
		val foundIssue = issueService.getIssueById(createdIssue.id)
		assertEquals(createdIssue.id, foundIssue.id)
		assertEquals("Find me", foundIssue.title)
	}

	@Test
	fun getAllIssuesTest() {
		issueService.createIssue(IssueRequestTo(title = "Issue 1", content = "Content 1", writerId = 1))
		issueService.createIssue(IssueRequestTo(title = "Issue 2", content = "Content 2", writerId = 1))
		val issues = issueService.getAllIssues()
		assertTrue(issues.any { it.title == "Issue 1" })
		assertTrue(issues.any { it.title == "Issue 2" })
	}

	@Test
	fun updateIssueTest() {
		val issueRequestTo = IssueRequestTo(title = "Old Title", content = "Old Content", writerId = 1)
		val createdIssue = issueService.createIssue(issueRequestTo)
		val updatedIssueRequestTo = IssueRequestTo(title = "New Title", content = "New Content", writerId = 1)
		Thread.sleep(10)
		val updatedIssue = issueService.updateIssue(createdIssue.id, updatedIssueRequestTo)
		assertEquals(createdIssue.id, updatedIssue.id)
		assertEquals("New Title", updatedIssue.title)
		assertEquals("New Content", updatedIssue.content)
		assertTrue(updatedIssue.modified.isAfter(createdIssue.modified))
	}

	@Test
	fun deleteIssueTest() {
		val issueRequestTo = IssueRequestTo(title = "Title", content = "Content", writerId = 1)
		val createdIssue = issueService.createIssue(issueRequestTo)
		issueService.deleteIssue(createdIssue.id)
		assertThrows<NoSuchElementException> {
			issueService.getIssueById(createdIssue.id)
		}
	}

	@Test
	fun filterIssuesTest() {
		writerRepository.save(
			Writer(
				login = "login",
				password = "",
				firstname = "",
				lastname = ""
			)
		)

		val label = Label(id = 1, name = "Bug")
		labelRepository.save(label)

		val issueRequestTo = IssueRequestTo(title = "Issue with bug", content = "Detailed bug description", writerId = 1)
		val createdIssue = issueService.createIssue(issueRequestTo)
		issueLabelRepository.save(IssueLabel(issueId = createdIssue.id, labelId = label.id))

		val filteredByLabelName = issueService.getIssuesByFilters(
			labelNames = listOf("buG"),
			labelIds = null,
			writerLogin = null,
			title = null,
			content = null
		)
		assertTrue(filteredByLabelName.any { it.id == createdIssue.id })

		val filteredByLabelId = issueService.getIssuesByFilters(
			labelNames = null,
			labelIds = listOf(1),
			writerLogin = null,
			title = null,
			content = null
		)
		assertTrue(filteredByLabelId.any { it.id == createdIssue.id })

		val filteredByWriter = issueService.getIssuesByFilters(
			labelNames = null,
			labelIds = null,
			writerLogin = "LoGin",
			title = null,
			content = null
		)
		assertTrue(filteredByWriter.any { it.id == createdIssue.id })

		val filteredByTitle = issueService.getIssuesByFilters(
			labelNames = null,
			labelIds = null,
			writerLogin = null,
			title = "issuE",
			content = null
		)
		assertTrue(filteredByTitle.any { it.id == createdIssue.id })

		val filteredByContent = issueService.getIssuesByFilters(
			labelNames = null,
			labelIds = null,
			writerLogin = null,
			title = null,
			content = "DetailEd"
		)
		assertTrue(filteredByContent.any { it.id == createdIssue.id })
	}
}
