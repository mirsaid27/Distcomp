using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;

namespace TaskREST.Services.Interfaces;

public interface ICommentService
{
    Task<CommentResponseTo> GetCommentById(long id);
    Task<IEnumerable<CommentResponseTo>> GetComments();
    Task<CommentResponseTo> CreateComment(CreateCommentRequestTo createCommentRequestTo);
    Task DeleteComment(long id);
    Task<CommentResponseTo> UpdateComment(UpdateCommentRequestTo modifiedComment);
}