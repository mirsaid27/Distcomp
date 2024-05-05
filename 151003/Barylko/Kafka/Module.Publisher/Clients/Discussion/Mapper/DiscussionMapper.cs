using Publisher.Clients.Discussion.Dto.Request;
using Publisher.Clients.Discussion.Dto.Response;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Riok.Mapperly.Abstractions;
namespace Publisher.Clients.Discussion.Mapper;

[Mapper]
public static partial class DiscussionMapper
{
    public static DiscussionCommentRequestTo ToDiscussionRequest(this CreateCommentRequestTo requestTo, long id, string country) =>
        new(id, requestTo.IssueId, requestTo.Content, country);
    public static DiscussionCommentRequestTo ToDiscussionRequest(this UpdateCommentRequestTo requestTo, string country) =>
        new(requestTo.Id, requestTo.IssueId, requestTo.Content, country);

    public static partial CommentResponseTo ToResponse(this DiscussionCommentResponseTo responseTo);

    public static partial IEnumerable<CommentResponseTo> ToResponse(this IEnumerable<DiscussionCommentResponseTo> responseTo);
}
