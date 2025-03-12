using System;
using Domain.Models;
using Domain.Repositories;
using Infrastructure.Errors;
using Infrastructure.Repositories.Interfaces;
using Infrastructure.Settings;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Driver;
using Shared.Domain;

namespace Infrastructure.Repositories.Mongo;

/*public static class ToMongoModelMapper*/
/*{*/
/*    public static ReactionMongoModel ToMongoModel(this ReactionModel model)*/
/*    {*/
/*        return new ReactionMongoModel*/
/*        {*/
/*            Id = model.Id.ToString(),*/
/*            TweetId = model.TweetId,*/
/*            Content = model.Content,*/
/*        };*/
/*    }*/
/*}*/

/*public static class ToModelMapper*/
/*{*/
/*    public static ReactionModel ToModel(this ReactionMongoModel model)*/
/*    {*/
/*        return new ReactionModel*/
/*        {*/
/*            Id = long.Parse(model.Id),*/
/*            TweetId = model.TweetId,*/
/*            Content = model.Content,*/
/*        };*/
/*    }*/
/*}*/

public class ReactionRepositoryMongo : IReactionRepository, IMongoRepository
{
    private readonly IMongoCollection<ReactionMongoModel> _reactionCollection;
    private long _idGenerator = 1;
    private ILogger<ReactionRepositoryMongo> _logger;
    private Dictionary<long, string> _numericIdToMongoId = new();
    private Dictionary<string, long> _mongoIdToNumericId = new();

    public ReactionRepositoryMongo(
        IOptions<MongoOptions> settings,
        ILogger<ReactionRepositoryMongo> logger
    )
    {
        var mongoClient = new MongoClient(settings.Value.MongoConnectionString);

        var mongoDatabase = mongoClient.GetDatabase(settings.Value.DatabaseName);

        _reactionCollection = mongoDatabase.GetCollection<ReactionMongoModel>(
            settings.Value.ReactionCollectionName
        );
        _logger = logger;
    }

    private long getNextId()
    {
        return _idGenerator++;
    }

    public async Task<Result<ReactionMongoModel>> CreateReaction(ReactionMongoModel reaction)
    {
        /*var reactionMongo = reaction.ToMongoModel();*/
        var newId = getNextId();
        var newMongoId = ObjectId.GenerateNewId().ToString();
        _numericIdToMongoId.Add(newId, newMongoId);
        _mongoIdToNumericId.Add(newMongoId, newId);
        reaction.Id = newMongoId;

        await _reactionCollection.InsertOneAsync(reaction);
        _logger.LogCritical(reaction.ToString());

        /*await _reactionCollection.FindAsync(x => x.Id == newId);*/
        /*return Result.Success<ReactionModel>(reactionMongo.ToModel());*/
        reaction.Id = newId.ToString();
        return reaction;

        /*catch (NpgsqlException ex) when (ex.SqlState == "23503")*/
        /*{*/
        /*    return Result.Failure<ReactionModel>(ReactionErrors.TweetForReactionNotFoundError);*/
        /*}*/
        /*catch (NpgsqlException ex)*/
        /*{*/
        /*    return Result.Failure<ReactionModel>(Error.DatabaseError);*/
        /*}*/
        /**/
        /*return Result.Failure<ReactionModel>(Error.Unknown);*/
    }

    public async Task<Result> DeleteReaction(long id)
    {
        var mongoId = _numericIdToMongoId[id];
        await _reactionCollection.DeleteOneAsync(x => x.Id == mongoId);
        return Result.Success();
        /*catch (NpgsqlException ex)*/
        /*{*/
        /*    return Result.Failure(Error.DatabaseError);*/
        /*}*/
        /**/
        /*return result == 0*/
        /*    ? Result.Failure(ReactionErrors.ReactionNotFoundError)*/
        /*    : Result.Success();*/
    }

    public async Task<Result<ReactionMongoModel>> GetReactionById(long id)
    {
        var mongoId = _numericIdToMongoId[id];
        var result = await _reactionCollection.Find(x => x.Id == mongoId).FirstOrDefaultAsync();
        result.Id = id.ToString();
        return result;
        /*catch (NpgsqlException ex)*/
        /* {*/
        /*     return Result.Failure<ReactionModel>(Error.DatabaseError);*/
        /* }*/
    }

    public async Task<Result<IEnumerable<ReactionMongoModel>>> GetReactions()
    {
        var result = await _reactionCollection.Find(_ => true).ToListAsync();
        result.ForEach(m => m.Id = _mongoIdToNumericId[m.Id].ToString());
        /*foreach (var r in result)*/
        /*{*/
        /*    result[r.Id] = _mongoIdToNumericId[r.Id].ToString();*/
        /*}*/
        /*result = result.Where(m => m.Id = _mongoIdToNumericId[m.Id].ToString());*/

        return Result.Success<IEnumerable<ReactionMongoModel>>(result);
        /*catch (NpgsqlException ex)*/
        /*{*/
        /*    return Result.Failure<IEnumerable<ReactionModel>>(Error.DatabaseError);*/
        /*}*/
    }

    public async Task<Result<ReactionMongoModel>> UpdateReaction(ReactionMongoModel reaction)
    {
        var prevId = reaction.Id;
        var mongoId = _numericIdToMongoId[long.Parse(reaction.Id)];
        reaction.Id = mongoId;

        await _reactionCollection.ReplaceOneAsync(x => x.Id == reaction.Id, reaction);
        reaction.Id = prevId;
        return reaction;
        /*catch (NpgsqlException ex) when (ex.SqlState == "23503")*/
        /*{*/
        /*    return Result.Failure<ReactionModel>(ReactionErrors.TweetForReactionNotFoundError);*/
        /*}*/
        /*catch (NpgsqlException ex)*/
        /*{*/
        /*    return Result.Failure<ReactionModel>(Error.DatabaseError);*/
        /*}*/
    }
}
