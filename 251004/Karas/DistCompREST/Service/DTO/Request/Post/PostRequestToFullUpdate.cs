namespace Service.DTO.Request.Post;

public class PostRequestToFullUpdate
{
    public long Id { get; init; }
    public long ArticleId { get; init; }
    public string Content { get; init; }
}