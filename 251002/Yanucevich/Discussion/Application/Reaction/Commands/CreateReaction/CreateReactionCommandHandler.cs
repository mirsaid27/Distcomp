using System;
using Application.Abstractions;
using Domain.Mappers;
using Domain.Models;
using Domain.Projections;
using Domain.Repositories;
using Shared.Domain;

namespace Application.Features.Reaction.Commands;

public class CreateReactionCommandHandler
    : ICommandHandler<CreateReactionCommand, ReactionMongoProjection>
{
    private readonly IReactionRepository _reactionRepository;

    public CreateReactionCommandHandler(IReactionRepository reactionRepository)
    {
        _reactionRepository = reactionRepository;
    }

    public async Task<Result<ReactionMongoProjection>> Handle(
        CreateReactionCommand request,
        CancellationToken cancellationToken
    )
    {
        var resultReaction = await _reactionRepository.CreateReaction(
            new ReactionMongoModel { TweetId = request.tweetId, Content = request.content }
        );

        if (!resultReaction.IsSuccess)
        {
            return Result.Failure<ReactionMongoProjection>(resultReaction.Error);
        }

        return resultReaction.Value.ToReactionMongoProjection();
    }
}
