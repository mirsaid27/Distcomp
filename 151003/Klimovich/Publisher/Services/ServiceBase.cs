using Publisher.Services.Interfaces;

namespace Publisher.Services
{
    public class ServiceBase : IServiceBase
    {
        public IUserService userService { get; }

        public ITweetService tweetService { get; }

        public ICommentService commentService { get; }

        public IStickerService stickerService { get; }

        public ServiceBase(
            IUserService userService,
            ITweetService tweetService,
            ICommentService commentService,
            IStickerService stickerService
            )
        {
            this.userService = userService;
            this.tweetService = tweetService;
            this.commentService = commentService;
            this.stickerService = stickerService;
        }
    }
}
