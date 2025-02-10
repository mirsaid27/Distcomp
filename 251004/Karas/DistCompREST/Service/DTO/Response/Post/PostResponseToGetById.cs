using System.Runtime.Serialization;

namespace Service.DTO.Response.Post;

[Serializable]
[DataContract(Name="post")]
public class PostResponseToGetById
{
    public long Id { get; init; }
    public long ArticleId { get; init; }
    public string Content { get; init; }
}