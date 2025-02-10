using AutoMapper;
using Domain.Entities;
using Service.DTO.Request.Article;
using Service.DTO.Response.Article;

namespace Service.Mapper;

public class ArticleMappingProfile : Profile
{
    public ArticleMappingProfile()
    {
        CreateMap<ArticleRequestToCreate, Article>();
        CreateMap<ArticleRequestToFullUpdate, Article>();
        CreateMap<Article, ArticleResponseToGetById>();
    }
}