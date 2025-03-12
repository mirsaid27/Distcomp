package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.IssueRequestTo
import bsuir.dc.rest.dto.to.IssueResponseTo
import bsuir.dc.rest.entity.Issue

fun IssueRequestTo.toEntity(): Issue = Issue(
    id = id,
    writerId = writerId,
    title = title,
    content = content,
)

fun Issue.toResponse(): IssueResponseTo = IssueResponseTo(
    id = id,
    writerId = writerId,
    title = title,
    content = content,
    created = created,
    modified = modified
)