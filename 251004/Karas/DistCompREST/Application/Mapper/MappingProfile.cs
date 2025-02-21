using Application.Dto.Request;
using Application.Dto.Response;
using AutoMapper;
using Domain.Entities;

namespace Application.Mapper;

public class MappingProfile : Profile
{
    public MappingProfile()
    {
        CreateMap<Editor, EditorResponseDto>();
        CreateMap<EditorRequestDto, Editor>();

        CreateMap<Post, PostResponseDto>();
        CreateMap<PostRequestDto, Post>();

        CreateMap<Mark, MarkResponseDto>();
        CreateMap<MarkRequestDto, Mark>();

        CreateMap<Article, ArticleResponseDto>();
        CreateMap<ArticleRequestDto, Article>();
    }
}