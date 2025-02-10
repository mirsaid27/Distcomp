using Service.DTO.Request;
using Service.DTO.Response;
using Service.DTO.Request.Article;
using Service.DTO.Response.Article;

namespace Service.Interfaces;

public interface IArticleService
{
    public Task<ArticleResponseToGetById?> CreateArticle(ArticleRequestToCreate model);
    public Task<IEnumerable<ArticleResponseToGetById?>?> GetArticle(ArticleRequestToGetAll request);
    public Task<ArticleResponseToGetById?> GetArticleById(ArticleRequestToGetById request);
    public Task<ArticleResponseToGetById?> UpdateArticle(ArticleRequestToFullUpdate model);
    public Task<ArticleResponseToGetById?> DeleteArticle(ArticleRequestToDeleteById request);
}