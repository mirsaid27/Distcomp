using System.Text.Json.Serialization;

namespace TaskREST.Dto.Response;

public record IssueResponseTo(
    [property: JsonPropertyName("id")] 
    long Id,
    [property: JsonPropertyName("userId")]
    long UserId,
    [property: JsonPropertyName("title")] 
    string Title,
    [property: JsonPropertyName("content")]
    string Content
);