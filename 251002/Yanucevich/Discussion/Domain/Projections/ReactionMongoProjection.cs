using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Driver;

public class ReactionMongoProjection
{
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public long Id { get; set; }

    public long TweetId { get; set; }
    public string Content { get; set; }
}
