using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;
using Infrastructure.Settings;
using Microsoft.Extensions.Options;

namespace Infrastructure.Repositories.Postgres;

public class TweetRepositoryPg : PgRepository, ITweetRepository
{
    public TweetRepositoryPg(IOptions<InfrastructureOptions> settings) : base(settings.Value)
    {
    }

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
