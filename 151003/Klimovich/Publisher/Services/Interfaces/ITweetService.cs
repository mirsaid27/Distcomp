using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;

namespace Publisher.Services.Interfaces
{
    public interface ITweetService
    {
        TweetResponceTo CreateTweet(TweetRequestTo item);
        List<TweetResponceTo> GetTweets();
        TweetResponceTo GetTweet(int id);
        TweetResponceTo UpdateTweet(TweetRequestTo item);
        int DeleteTweet(int id);
    }
}
