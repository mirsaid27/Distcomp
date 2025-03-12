package bsuir.dc.rest

import bsuir.dc.rest.dto.from.LabelRequestTo
import bsuir.dc.rest.entity.IssueLabel
import bsuir.dc.rest.repository.memory.IssueLabelInMemoryRepository
import bsuir.dc.rest.service.LabelService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LabelServiceTest {

    @Autowired
    private lateinit var labelService: LabelService

    @Autowired
    private lateinit var issueLabelRepository: IssueLabelInMemoryRepository

    @Test
    fun createLabelTest() {
        val labelRequestTo = LabelRequestTo(name = "Test Label")
        val createdLabel = labelService.createLabel(labelRequestTo)
        assertNotNull(createdLabel.id)
        assertEquals("Test Label", createdLabel.name)
    }

    @Test
    fun getLabelByIdTest() {
        val labelRequestTo = LabelRequestTo(name = "Label 1")
        val createdLabel = labelService.createLabel(labelRequestTo)
        val foundLabel = labelService.getLabelById(createdLabel.id)
        assertEquals(createdLabel.id, foundLabel.id)
        assertEquals("Label 1", foundLabel.name)
    }

    @Test
    fun getAllLabelsTest() {
        labelService.createLabel(LabelRequestTo(name = "Label 1"))
        labelService.createLabel(LabelRequestTo(name = "Label 2"))
        val labels = labelService.getAllLabels()
        assertTrue(labels.any { it.name == "Label 1" })
        assertTrue(labels.any { it.name == "Label 2" })
    }

    @Test
    fun updateLabelTest() {
        val labelRequestTo = LabelRequestTo(name = "Old Label")
        val createdLabel = labelService.createLabel(labelRequestTo)
        val updatedLabelRequestTo = LabelRequestTo(name = "New Label")
        val updatedLabel = labelService.updateLabel(createdLabel.id, updatedLabelRequestTo)
        assertEquals(createdLabel.id, updatedLabel.id)
        assertEquals("New Label", updatedLabel.name)
    }

    @Test
    fun deleteLabelTest() {
        val labelRequestTo = LabelRequestTo(name = "To Delete")
        val createdLabel = labelService.createLabel(labelRequestTo)
        labelService.deleteLabel(createdLabel.id)
        assertThrows<NoSuchElementException> {
            labelService.getLabelById(createdLabel.id)
        }
    }

    @Test
    fun getLabelsByIssueIdTest() {
        val labelRequestTo = LabelRequestTo(name = "Bug")
        val createdLabel = labelService.createLabel(labelRequestTo)
        issueLabelRepository.save(IssueLabel(issueId = 1, labelId = createdLabel.id))
        val labels = labelService.getLabelsByIssueId(1)
        assertTrue(labels.any { it.id == createdLabel.id })
    }
}
