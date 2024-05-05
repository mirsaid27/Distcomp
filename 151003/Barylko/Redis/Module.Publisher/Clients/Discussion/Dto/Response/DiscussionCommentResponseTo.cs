using System.Text.Json.Serialization;
namespace Publisher.Clients.Discussion.Dto.Response;

public record DiscussionCommentResponseTo(
    [property: JsonPropertyName("id")]
    long Id,
    [property: JsonPropertyName("issueId")]
    long IssueId,
    [property: JsonPropertyName("content")]
    string Content
);