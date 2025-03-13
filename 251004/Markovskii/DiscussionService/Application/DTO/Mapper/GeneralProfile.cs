using Application.DTO.Request.Post;
using Application.DTO.Response.Post;
using AutoMapper;
using Domain.Entities;

namespace Application.DTO.Mapper;

public class GeneralProfile : Profile
{
    public GeneralProfile()
    {
        CreateMap<PostRequestToCreate, PostMongoDb>();
        CreateMap<PostRequestToFullUpdate, PostMongoDb>();
        CreateMap<PostMongoDb, PostResponseToGetById>();
    }
}