using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;
using Infrastructure.Settings;
using Microsoft.Extensions.Options;

namespace Infrastructure.Repositories.Postgres;

public class ReactionRepositoryPg : PgRepository, IReactionRepository
{
    public ReactionRepositoryPg(IOptions<InfrastructureOptions> settings) : base(settings.Value)
    {
    }

    public Task<Result<ReactionModel>> CreateReaction(ReactionModel reaction)
    {
        throw new NotImplementedException();
    }

    public Task<Result> DeleteReaction(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<ReactionModel>> GetReactionById(long id)
    {
        throw new NotImplementedException();
    }

    public Task<Result<IEnumerable<ReactionModel>>> GetReactions()
    {
        throw new NotImplementedException();
    }

    public Task<Result<ReactionModel>> UpdateReaction(ReactionModel reaction)
    {
        throw new NotImplementedException();
    }
}
