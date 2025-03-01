using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Domain.Entities;

public class Sequence
{
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string Key { get; set; } = null!;
    
    public string CollectionName { get; set; } = null!;
    public long CurrentId { get; set; }
}