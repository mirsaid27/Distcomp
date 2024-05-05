using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
namespace Discussion.Dto.Request;

public record CommentRequestTo(
    [property: JsonPropertyName("id")]
    long Id,
    [property: JsonPropertyName("issueId")]
    long IssueId,
    [property: JsonPropertyName("content")]
    [StringLength(2048, MinimumLength = 2)]
    string Content,
    [property: JsonPropertyName("country")]
    [StringLength(2, MinimumLength = 2)]
    string Country
);