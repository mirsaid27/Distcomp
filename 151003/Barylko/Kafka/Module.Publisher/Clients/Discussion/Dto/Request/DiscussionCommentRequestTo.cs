using System.Text.Json.Serialization;
namespace Publisher.Clients.Discussion.Dto.Request;

public record DiscussionCommentRequestTo(
    [property: JsonPropertyName("id")] 
    long Id,
    [property: JsonPropertyName("issueId")]
    long IssueId,
    [property: JsonPropertyName("content")]
    string Content,
    [property: JsonPropertyName("country")]
    string Country
)
{
    public DiscussionCommentRequestTo(long id) : this(id, 0, string.Empty, string.Empty)
    {}

    public DiscussionCommentRequestTo() : this(0, 0, string.Empty, string.Empty)
    {}
}
