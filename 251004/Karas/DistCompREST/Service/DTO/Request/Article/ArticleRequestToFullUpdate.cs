namespace Service.DTO.Request.Article;

public class ArticleRequestToFullUpdate
{
    public long Id { get; init; }
    public long EditorId { get; init; }
    public string Title { get; init; }
    public string Content { get; init; }
}