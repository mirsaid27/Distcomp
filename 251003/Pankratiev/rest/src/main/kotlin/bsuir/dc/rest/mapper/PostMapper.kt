package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.PostRequestTo
import bsuir.dc.rest.dto.to.PostResponseTo
import bsuir.dc.rest.entity.Issue
import bsuir.dc.rest.entity.Post

fun PostRequestTo.toEntity(issue: Issue): Post {
    return Post(
        id = this.id,
        issue = issue,
        content = this.content,
    )
}

fun Post.toResponse(): PostResponseTo {
    return PostResponseTo(
        id = this.id,
        issueId = this.issue.id,
        content = this.content
    )
}
