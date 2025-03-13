using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Domain.Entities;

public class PostMongoDb
{
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string MongoId { get; set; } = string.Empty;
    
    [BsonElement("id")]
    public long Id { get; set; }
    
    [BsonElement("country")]
    public string Country { get; set; } = string.Empty;
        
    [BsonElement("newsId")]
    public long NewsId { get; set; }
        
    [BsonElement("content")]
    public string Content { get; set; } = string.Empty;
}