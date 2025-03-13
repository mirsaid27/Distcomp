using Domain.Entities;
using Domain.Repositories;
using Infrastructure.Settings;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Driver;

namespace Infrastructure.Repositories.MongoDb;

public class PostMongoDbRepository : IPostRepository, IMongoDbRepository
{
    private readonly IMongoCollection<PostMongoDb> _posts;
    
    public PostMongoDbRepository(
        IOptions<MongoDbOptions> settings
    )
    {
        var mongoClient = new MongoClient(settings.Value.MongoConnectionString);

        var mongoDatabase = mongoClient.GetDatabase(settings.Value.DatabaseName);

        _posts = mongoDatabase.GetCollection<PostMongoDb>(
            settings.Value.PostCollectionName
        );
        
        var indexKeysDefinition = Builders<PostMongoDb>.IndexKeys.Ascending(p => p.Id);
        var indexModel = new CreateIndexModel<PostMongoDb>(indexKeysDefinition, new CreateIndexOptions { Unique = true });
        _posts.Indexes.CreateOne(indexModel);   
    }
    
    public async Task<PostMongoDb?> AddPost(PostMongoDb post)
    {
        var maxPost = await _posts.Find(_ => true).SortByDescending(p => p.Id).Limit(1).FirstOrDefaultAsync();
        post.Id = (maxPost?.Id ?? 0) + 1;
        
        var newMongoId = ObjectId.GenerateNewId().ToString();
        post.MongoId = newMongoId;

        await _posts.InsertOneAsync(post);
        
        return post;
    }

    public async Task<PostMongoDb?> GetPost(long postId)
    {
        var result = await _posts.Find(x => x.Id == postId).FirstOrDefaultAsync();
        return result;
    }

    public async Task<PostMongoDb?> RemovePost(long postId)
    {
        var post = await _posts.FindOneAndDeleteAsync(p => p.Id == postId);
        return post;
    }

    public async Task<PostMongoDb?> UpdatePost(PostMongoDb post)
    {
        var postInDb = await _posts.Find(x => x.Id == post.Id).FirstOrDefaultAsync();
        if (postInDb == null)
        {
            return null;
        }
        post.MongoId = postInDb.MongoId;
        var result = await _posts.ReplaceOneAsync(p => p.Id == post.Id, post);
        return result.IsAcknowledged && result.ModifiedCount > 0 ? post : null;
    }

    public async Task<IEnumerable<PostMongoDb?>?> GetAllPosts()
    {
        var posts = await _posts.Find(_ => true).ToListAsync();
        return posts;
    }

    public async Task<IEnumerable<PostMongoDb?>?> GetPostsByNewsId(long newsId)
    {
        var posts = await _posts.Find(p => p.NewsId == newsId).ToListAsync();
        return posts;
    }
}