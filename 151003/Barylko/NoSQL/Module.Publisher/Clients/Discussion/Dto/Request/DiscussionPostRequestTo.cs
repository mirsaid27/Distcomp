using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
namespace Publisher.Clients.Discussion.Dto.Request;

public record DiscussionCommentRequestTo(
    long Id,
    long IssueId,
    [StringLength(2048, MinimumLength = 2)]
    string Content,
    string Country
);