using Publisher.Clients.Discussion.Dto.Request;
using Publisher.Clients.Discussion.Dto.Response;
using Refit;
namespace Publisher.Clients.Discussion;

public interface IDiscussionService
{
    [Get("/comments/{id}")]
    Task<DiscussionCommentResponseTo> GetComment(long id);

    [Get("/comments")]
    Task<IEnumerable<DiscussionCommentResponseTo>> GetComments();

    [Post("/comments")]
    Task<DiscussionCommentResponseTo> CreateComment([Body] DiscussionCommentRequestTo createCommentRequestTo);

    [Delete("/comments/{id}")]
    Task DeleteComment(long id);

    [Put("/comments")]
    Task<DiscussionCommentResponseTo> UpdateComment([Body] DiscussionCommentRequestTo CommentRequestTo);
}