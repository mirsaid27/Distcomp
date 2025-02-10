using Service.DTO.Request;
using Service.DTO.Response;
using AutoMapper;
using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;
using Service.DTO.Request.Article;
using Service.DTO.Response.Article;
using Service.Interfaces;

namespace Service.Services;

public class ArticleService(IArticleRepository articleRepository, IMapper mapper) : IArticleService
{
    public async Task<ArticleResponseToGetById?> CreateArticle(ArticleRequestToCreate model)
    {
        var article = mapper.Map<Article>(model);
        Validate(article);
        article = await articleRepository.AddArticle(article);
        return mapper.Map<ArticleResponseToGetById>(article);
    }

    public async Task<IEnumerable<ArticleResponseToGetById?>?> GetArticle(ArticleRequestToGetAll request)
    {
        var article = await articleRepository.GetAllArticle();
        return article.Select(mapper.Map<ArticleResponseToGetById>);
    }

    public async Task<ArticleResponseToGetById?> GetArticleById(ArticleRequestToGetById request)
    {
        var article = await articleRepository.GetArticle(request.Id);
        return mapper.Map<ArticleResponseToGetById>(article);
    }

    public async Task<ArticleResponseToGetById?> UpdateArticle(ArticleRequestToFullUpdate model)
    {
        var article = mapper.Map<Article>(model);
        Validate(article);
        article = await articleRepository.UpdateArticle(article);
        return mapper.Map<ArticleResponseToGetById>(article);
    }

    public async Task<ArticleResponseToGetById?> DeleteArticle(ArticleRequestToDeleteById request)
    {
        var article = await articleRepository.RemoveArticle(request.Id);
        return mapper.Map<ArticleResponseToGetById>(article);
    }
    
    private bool Validate(Article article)
    {
        var errors = new Dictionary<string, string[]>();
        
        if (article.Title.Length < 2 || article.Title.Length > 64)
        {
            errors.Add("Title",["Should be from 2 to 64 chars"]);
        }
        
        if (article.Content.Length < 4 || article.Content.Length > 2048)
        {
            errors.Add("Content",["Should be from 2 to 64 chars"]);
        }
        
        if (errors.Count != 0)
        {
            throw new BadRequestException("Validation error", errors);
        }
        
        return true;
    }
}