package bsuir.dc.rest.service

import bsuir.dc.rest.dto.from.LabelRequestTo
import bsuir.dc.rest.dto.to.LabelResponseTo
import bsuir.dc.rest.mapper.toEntity
import bsuir.dc.rest.mapper.toResponse
import bsuir.dc.rest.repository.memory.IssueLabelInMemoryRepository
import bsuir.dc.rest.repository.memory.LabelInMemoryRepository
import org.springframework.stereotype.Service

@Service
class LabelService(
    private val labelRepository: LabelInMemoryRepository,
    private val issueLabelRepository: IssueLabelInMemoryRepository,
) {
    fun createLabel(labelRequestTo: LabelRequestTo): LabelResponseTo {
        val label = labelRequestTo.toEntity()
        val savedLabel = labelRepository.save(label)
        return savedLabel.toResponse()
    }

    fun getLabelById(id: Long): LabelResponseTo {
        val label = labelRepository.findById(id)
        return label.toResponse()
    }

    fun getAllLabels(): List<LabelResponseTo> =
        labelRepository.findAll().map { it.toResponse() }

    fun updateLabel(id: Long, labelRequestTo: LabelRequestTo): LabelResponseTo {
        val updatedLabel = labelRequestTo.toEntity().apply { this.id = id }
        return labelRepository.update(updatedLabel).toResponse()
    }

    fun deleteLabel(id: Long) {
        labelRepository.deleteById(id)
    }

    fun getLabelsByIssueId(issueId: Long): List<LabelResponseTo> {
        val issueLabels = issueLabelRepository.findAll().filter { it.issueId == issueId }
        return issueLabels.map { getLabelById(it.labelId) }
    }
}