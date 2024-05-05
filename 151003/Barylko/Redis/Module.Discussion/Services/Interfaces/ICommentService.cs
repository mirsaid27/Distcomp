using Discussion.Dto.Request;
using Discussion.Dto.Response;
namespace Discussion.Services.Interfaces;

public interface ICommentService
{
    Task<CommentResponseTo> GetCommentById(long id);
    Task<IEnumerable<CommentResponseTo>> GetComments();
    Task<CommentResponseTo> CreateComment(CommentRequestTo CommentRequestTo);
    Task DeleteComment(long id);
    Task<CommentResponseTo> UpdateComment(CommentRequestTo modifiedComment);
}