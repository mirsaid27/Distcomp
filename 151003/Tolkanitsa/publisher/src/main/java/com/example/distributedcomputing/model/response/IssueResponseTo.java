package com.example.distributedcomputing.model.response;

public record IssueResponseTo(
        Long id,
        String title,
        String content,
        Long creatorId
) {
}
