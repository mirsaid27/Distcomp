using Discussion.Dto.Request;
using Discussion.Dto.Response;
using Discussion.Models;
using Riok.Mapperly.Abstractions;
namespace Discussion.Mappers;

[Mapper]
public static partial class CommentMapper
{
    public static partial Comment ToEntity(this CommentRequestTo CommentRequestTo);
    public static partial CommentResponseTo ToResponse(this Comment Comment);
    public static partial IEnumerable<CommentResponseTo> ToResponse(this IEnumerable<Comment> Comments);

    public static Comment UpdateEntity(this Comment Comment, CommentRequestTo updateCommentRequestTo) => new()
    {
        Id = Comment.Id,
        IssueId = updateCommentRequestTo.IssueId,
        Content = updateCommentRequestTo.Content,
        Country = Comment.Country
    };
}
