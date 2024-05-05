using System.Text.Json.Serialization;
namespace Publisher.Dto.Response;

public record CommentResponseTo(
    [property: JsonPropertyName("id")] 
    long Id,
    [property: JsonPropertyName("issueId")]
    long IssueId,
    [property: JsonPropertyName("content")]
    string Content
);