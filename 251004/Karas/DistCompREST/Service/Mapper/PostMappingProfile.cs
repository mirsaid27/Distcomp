using AutoMapper;
using Domain.Entities;
using Service.DTO.Request.Post;
using Service.DTO.Response.Post;

namespace Service.Mapper;

public class PostMappingProfile : Profile
{
    public PostMappingProfile()
    {
        CreateMap<PostRequestToCreate, Post>();
        CreateMap<PostRequestToFullUpdate, Post>();
        CreateMap<Post, PostResponseToGetById>();
    }
}