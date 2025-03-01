using Domain.Interfaces;
using MongoDB.Bson;
using MongoDB.Driver;

namespace Persistence.Repositories;

public abstract class RepositoryBase<T> : IRepositoryBase<T> where T : class
{
    private readonly IMongoCollection<T> _collection;

    public RepositoryBase(IMongoDatabase database, string collectionName)
    {
        _collection = database.GetCollection<T>(collectionName);
    }

    public async Task<List<T>> GetAllAsync(CancellationToken cancellationToken = default)
    {
        return await _collection.Find(new BsonDocument()).ToListAsync(cancellationToken);
    }

    public async Task<T> GetByIdAsync(long id, CancellationToken cancellationToken = default)
    {
        return await _collection.Find(Builders<T>.Filter.Eq("Id", id)).FirstOrDefaultAsync(cancellationToken);
    }

    public async Task AddAsync(T entity, CancellationToken cancellationToken = default)
    {
        await _collection.InsertOneAsync(entity, cancellationToken: cancellationToken);
    }

    public async Task<T> UpdateAsync(long id, T entity, CancellationToken cancellationToken = default)
    {
        var existingEntity = await _collection.Find(Builders<T>.Filter.Eq("Id", id)).FirstOrDefaultAsync(cancellationToken);
        var entityType = typeof(T);
        var idProperty = entityType.GetProperty("Key");
        if (idProperty != null)
        {
            idProperty.SetValue(entity, idProperty.GetValue(existingEntity));
        }

        return await _collection.FindOneAndReplaceAsync(
            Builders<T>.Filter.Eq("Id", id), 
            entity, 
            new FindOneAndReplaceOptions<T> { ReturnDocument = ReturnDocument.After },
            cancellationToken);
    }

    public async Task DeleteAsync(long id, CancellationToken cancellationToken = default)
    {
        await _collection.DeleteOneAsync(Builders<T>.Filter.Eq("Id", id), cancellationToken);
    }
}