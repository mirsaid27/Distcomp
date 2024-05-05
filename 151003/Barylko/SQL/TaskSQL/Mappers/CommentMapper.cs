using Riok.Mapperly.Abstractions;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Models;

namespace TaskREST.Mappers;

[Mapper]
public static partial class CommentMapper
{
    public static partial Comment Map(UpdateCommentRequestTo updateCommentRequestTo);
    public static partial Comment Map(CreateCommentRequestTo createCommentRequestTo);
    public static partial CommentResponseTo Map(Comment Comment);
    public static partial IEnumerable<CommentResponseTo> Map(IEnumerable<Comment> Comments);

    public static partial IEnumerable<Comment> Map(
        IEnumerable<UpdateCommentRequestTo> CommentRequestTos);
}