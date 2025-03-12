using System;
using Domain.Models;
using MongoDB.Bson;
using Shared.Domain;

namespace Domain.Repositories;

public interface IReactionRepository
{
    Task<Result<ReactionMongoModel>> CreateReaction(ReactionMongoModel reaction);
    Task<Result<IEnumerable<ReactionMongoModel>>> GetReactions();
    Task<Result<ReactionMongoModel>> GetReactionById(long id);
    Task<Result<ReactionMongoModel>> UpdateReaction(ReactionMongoModel reaction);
    Task<Result> DeleteReaction(long id);
}
