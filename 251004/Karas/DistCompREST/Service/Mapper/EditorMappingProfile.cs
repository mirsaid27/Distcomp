using AutoMapper;
using Domain.Entities;
using Service.DTO.Request.Editor;
using Service.DTO.Response.Editor;

namespace Service.Mapper;

public class EditorMappingProfile : Profile
{
    public EditorMappingProfile()
    {
        CreateMap<EditorRequestToCreate, Editor>();
        CreateMap<EditorRequestToFullUpdate, Editor>();
        CreateMap<Editor, EditorResponseToGetById>();
    }
}