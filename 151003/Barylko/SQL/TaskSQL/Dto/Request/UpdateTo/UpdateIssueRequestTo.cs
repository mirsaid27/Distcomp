using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace TaskREST.Dto.Request.UpdateTo;

public record UpdateIssueRequestTo(
    [property: JsonPropertyName("id")] 
    long id,
    [property: JsonPropertyName("userId")]
    long user_id,
    [property: JsonPropertyName("title")]
    [StringLength(64, MinimumLength = 2)]
    string Title,
    [property: JsonPropertyName("content")]
    [StringLength(2048, MinimumLength = 4)]
    string Content
);