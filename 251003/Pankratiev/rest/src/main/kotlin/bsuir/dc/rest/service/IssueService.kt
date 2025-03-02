package bsuir.dc.rest.service

import bsuir.dc.rest.dto.from.IssueFrom
import bsuir.dc.rest.dto.to.IssueTo
import bsuir.dc.rest.mapper.toEntity
import bsuir.dc.rest.mapper.toResponse
import bsuir.dc.rest.repository.memory.IssueInMemoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class IssueService(
    private val issueRepository: IssueInMemoryRepository,
) {
    fun createIssue(issueFrom: IssueFrom): IssueTo {
        val issue = issueFrom.toEntity()
        val savedIssue = issueRepository.save(issue)
        return savedIssue.toResponse()
    }

    fun getIssueById(id: Long): IssueTo {
        val issue = issueRepository.findById(id)
        return issue.toResponse()
    }

    fun getAllIssues(): List<IssueTo> =
        issueRepository.findAll().map { it.toResponse() }

    fun updateIssue(id: Long, issueFrom: IssueFrom): IssueTo {
        val updatedIssue = issueFrom.toEntity().apply {
            this.id = id
            this.modified = LocalDateTime.now()
        }
        return issueRepository.update(updatedIssue).toResponse()
    }

    fun deleteIssue(id: Long) {
        issueRepository.deleteById(id)
    }
}