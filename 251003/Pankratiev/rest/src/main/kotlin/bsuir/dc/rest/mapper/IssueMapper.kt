package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.IssueRequestTo
import bsuir.dc.rest.dto.to.IssueResponseTo
import bsuir.dc.rest.entity.Issue
import bsuir.dc.rest.entity.Label
import bsuir.dc.rest.entity.Writer

fun IssueRequestTo.toEntity(writer: Writer, labels: MutableSet<Label>) = Issue(
    id = this.id,
    writer = writer,
    title = this.title,
    content = this.content,
    labels = labels,
)

fun Issue.toResponse() = IssueResponseTo(
    id = this.id,
    writerId = this.writer.id,
    title = this.title,
    content = this.content,
    created = this.created,
    modified = this.modified
)
