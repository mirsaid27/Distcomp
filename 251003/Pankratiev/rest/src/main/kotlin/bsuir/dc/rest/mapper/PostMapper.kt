package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.PostRequestTo
import bsuir.dc.rest.dto.to.PostResponseTo
import bsuir.dc.rest.entity.Post

fun PostRequestTo.toEntity(): Post = Post(
    id = id,
    issueId = issueId,
    content = content
)

fun Post.toResponse(): PostResponseTo = PostResponseTo(
    id = id,
    issueId = issueId,
    content = content
)
