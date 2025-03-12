using System;
using Application.Abstractions;
using Domain.Mappers;
using Domain.Projections;
using Domain.Repositories;
using Shared.Domain;

namespace Application.Features.Reaction.Queries;

public class GetReactionsQueryHandler
    : IQueryHandler<GetReactionsQuery, IEnumerable<ReactionMongoProjection>>
{
    private readonly IReactionRepository _reactionRepository;

    public GetReactionsQueryHandler(IReactionRepository reactionRepository)
    {
        _reactionRepository = reactionRepository;
    }

    public async Task<Result<IEnumerable<ReactionMongoProjection>>> Handle(
        GetReactionsQuery request,
        CancellationToken cancellationToken
    )
    {
        var resultReactions = await _reactionRepository.GetReactions();

        if (!resultReactions.IsSuccess)
        {
            return Result.Failure<IEnumerable<ReactionMongoProjection>>(resultReactions.Error);
        }

        return Result.Success(resultReactions.Value.Select(r => r.ToReactionMongoProjection()));
    }
}
