using Publisher.Clients.Discussion.Dto.Request;
using Publisher.Clients.Discussion.Dto.Response;
namespace Publisher.Clients.Discussion;

public interface IDiscussionService
{
    Task<DiscussionCommentResponseTo> GetComment(long id);
    
    Task<IEnumerable<DiscussionCommentResponseTo>> GetComments();
    
    Task<DiscussionCommentResponseTo> CreateComment(DiscussionCommentRequestTo createCommentRequestTo);
    
    Task DeleteComment(long id);
    
    Task<DiscussionCommentResponseTo> UpdateComment(DiscussionCommentRequestTo postRequestTo);
}