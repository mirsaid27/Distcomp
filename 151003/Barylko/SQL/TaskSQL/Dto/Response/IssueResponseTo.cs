using System.Text.Json.Serialization;

namespace TaskREST.Dto.Response;

public record IssueResponseTo(
    [property: JsonPropertyName("id")] 
    long id,
    [property: JsonPropertyName("userId")]
    long user_id,
    [property: JsonPropertyName("title")] 
    string Title,
    [property: JsonPropertyName("content")]
    string Content
);