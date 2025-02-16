using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;

namespace Infrastructure.Repositories.Postgres;

public class TweetRepositoryPg : IPgRepository, ITweetRepository
{
    public Task<Result<TweetModel>> CreateTweet(TweetModel tweet)
    {
        throw new NotImplementedException();
    }

    public Task<Result> DeleteTweet(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<TweetModel>> GetTweetById(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<IEnumerable<TweetModel>>> GetTweets()
    {
        throw new NotImplementedException();
    }

    public Task<Result<TweetModel>> UpdateTweet(TweetModel tweet)
    {
        throw new NotImplementedException();
    }
}
