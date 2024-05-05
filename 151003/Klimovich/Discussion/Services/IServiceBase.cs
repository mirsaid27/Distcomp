using Discussion.Models.DTO.RequestTo;
using Discussion.Models.DTO.ResponceTo;
using Discussion.Services.Interfaces;

namespace Discussion.Services
{
    public interface IServiceBase : ICommentService
    {
        public ICommentService commentService { get; }

        CommentResponceTo ICommentService.CreateComment(CommentRequestTo item)
        {
            return commentService.CreateComment(item);
        }

        List<CommentResponceTo> ICommentService.GetComments()
        {
            return commentService.GetComments();
        }

        CommentResponceTo ICommentService.GetComment(int id)
        {
            return commentService.GetComment(id);
        }

        CommentResponceTo ICommentService.UpdateComment(CommentRequestTo item)
        {
            return commentService.UpdateComment(item);
        }

        int ICommentService.DeleteComment(int id)
        {
            return commentService.DeleteComment(id);
        }

    }
}
