using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Services.Interfaces;
using Publisher.Models.DTO.DTOs;
using Publisher.Models.DTO.ResponseTo;
using Publisher.Services.Realisation;
using DC.Models.DTOs.ResponceTo;

namespace Publisher.Services
{
    ///p:UseAppHost=false
    public interface IServiceBase : IUserService, ITweetService, ICommentService, IStickerService
    {
        public IUserService userService { get; }
        public ITweetService tweetService { get; }
        public ICommentService commentService { get; }
        public IStickerService stickerService { get; }

        UserResponceTo IUserService.CreateUser(UserRequestTo item)
        {
            return userService.CreateUser(item);
        }

        List<UserResponceTo> IUserService.GetUsers()
        {
            return userService.GetUsers();
        }

        UserResponceTo IUserService.GetUser(int id)
        {
            return userService.GetUser(id);
        }

        UserResponceTo IUserService.UpdateUser(UserRequestTo item)
        {
            return userService.UpdateUser(item);
        }

        int IUserService.DeleteUser(int id)
        {
            return userService.DeleteUser(id);
        }

        TweetResponceTo ITweetService.CreateTweet(TweetRequestTo item)
        {
            return tweetService.CreateTweet(item);
        }

        List<TweetResponceTo> ITweetService.GetTweets()
        {
            return tweetService.GetTweets();
        }

        TweetResponceTo ITweetService.GetTweet(int id)
        {
            return tweetService.GetTweet(id);
        }

        TweetResponceTo ITweetService.UpdateTweet(TweetRequestTo item)
        {
            return tweetService.UpdateTweet(item);
        }

        int ITweetService.DeleteTweet(int id)
        {
            return tweetService.DeleteTweet(id);
        }

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

        StickerResponceTo IStickerService.CreateSticker(StickerRequestTo item)
        {
            return stickerService.CreateSticker(item);
        }

        List<StickerResponceTo> IStickerService.GetStickers()
        {
            return stickerService.GetStickers();
        }

        StickerResponceTo IStickerService.GetSticker(int id)
        {
            return stickerService.GetSticker(id);
        }

        StickerResponceTo IStickerService.UpdateSticker(StickerRequestTo item)
        {
            return stickerService.UpdateSticker(item);
        }

        int IStickerService.DeleteSticker(int id)
        {
            return stickerService.DeleteSticker(id);
        }
    }
}
