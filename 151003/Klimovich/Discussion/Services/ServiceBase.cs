using Discussion.Services.Interfaces;

namespace Discussion.Services
{
    public class ServiceBase : IServiceBase
    {
        public ICommentService commentService { get; }

        public ServiceBase(
            ICommentService commentService
            )
        {
            this.commentService = commentService;
        }
    }
}
