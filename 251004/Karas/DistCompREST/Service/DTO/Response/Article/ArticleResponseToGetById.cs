using System.Runtime.Serialization;

namespace Service.DTO.Response.Article;

[Serializable]
[DataContract(Name="article")]
public class ArticleResponseToGetById
{
    public long Id { get; init; }
    public long EditorId { get; init; }
    public string Title { get; init; }
    public string Content { get; init; }
}