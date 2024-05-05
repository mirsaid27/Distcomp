using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
namespace Publisher.Services.Interfaces;

public interface ICommentService
{
    Task<CommentResponseTo> GetCommentById(long id);
    Task<IEnumerable<CommentResponseTo>> GetComments();
    Task<CommentResponseTo> CreateComment(CreateCommentRequestTo createCommentRequestTo, string country);
    Task DeleteComment(long id);
    Task<CommentResponseTo> UpdateComment(UpdateCommentRequestTo modifiedComment, string country);
}