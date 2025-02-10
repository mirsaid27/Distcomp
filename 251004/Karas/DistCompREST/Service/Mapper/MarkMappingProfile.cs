using AutoMapper;
using Domain.Entities;
using Service.DTO.Request.Mark;
using Service.DTO.Response.Mark;

namespace Service.Mapper;

public class MarkMappingProfile : Profile
{
    public MarkMappingProfile()
    {
        CreateMap<MarkRequestToCreate, Mark>();
        CreateMap<MarkRequestToFullUpdate, Mark>();
        CreateMap<Mark, MarkResponseToGetById>();
    }
}