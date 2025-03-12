package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.IssueLabelRequestTo
import bsuir.dc.rest.dto.to.IssueLabelResponseTo
import bsuir.dc.rest.entity.IssueLabel

fun IssueLabelRequestTo.toEntity(): IssueLabel = IssueLabel(
    issueId = issueId,
    labelId = labelId,
)

fun IssueLabel.toResponse(): IssueLabelResponseTo = IssueLabelResponseTo(
    issueId = issueId,
    labelId = labelId,
)
