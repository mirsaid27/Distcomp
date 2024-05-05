using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;

namespace Publisher.Services.Interfaces
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
