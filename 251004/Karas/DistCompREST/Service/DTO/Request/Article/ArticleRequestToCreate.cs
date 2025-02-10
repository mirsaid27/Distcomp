namespace Service.DTO.Request.Article;

public class ArticleRequestToCreate
{
    public long EditorId { get; init; }
    public string Title { get; init; }
    public string Content { get; init; }
}