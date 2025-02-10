namespace Domain.Entities;

public class Post
{
    public long Id { get; set; }
    public long ArticleId { get; set; }
    public string Content { get; set; }
}