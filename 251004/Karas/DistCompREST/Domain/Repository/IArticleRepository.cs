using Domain.Entities;

namespace Domain.Repository;

public interface IArticleRepository
{
    Task<Article?> AddArticle(Article article);
    Task<Article?> GetArticle(long articleId);
    Task<Article?> RemoveArticle(long articleId);
    Task<Article?> UpdateArticle(Article article);

    Task<IEnumerable<Article?>?> GetAllArticle();
}