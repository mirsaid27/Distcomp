using Discussion.Models.DTO.RequestTo;
using Discussion.Models.DTO.ResponceTo;

namespace Discussion.Services.Interfaces
{
    public interface ICommentService
    {
        CommentResponceTo CreateComment(CommentRequestTo item);
        List<CommentResponceTo> GetComments();
        CommentResponceTo GetComment(int id);
        CommentResponceTo UpdateComment(CommentRequestTo item);
        int DeleteComment(int id);
    }
}
