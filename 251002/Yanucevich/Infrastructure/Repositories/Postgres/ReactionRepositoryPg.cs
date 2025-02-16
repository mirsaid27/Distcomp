using System;
using Domain.Models;
using Domain.Repositories;
using Domain.Shared;
using Infrastructure.Repositories.Interfaces;

namespace Infrastructure.Repositories.Postgres;

public class ReactionRepositoryPg : IPgRepository, IReactionRepository
{
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
