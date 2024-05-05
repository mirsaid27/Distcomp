using System.Text.Json.Serialization;
namespace Publisher.Clients.Discussion.Dto.Response;

public record DiscussionCommentResponseTo(
    long Id,
    long IssueId,
    string Content
);