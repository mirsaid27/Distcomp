using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Models;

namespace dc_rest.Utilities.Mapper;

public class MappingConfig : Profile
{
    public MappingConfig()
    {
        CreateMap<Creator, CreatorResponseDto>();
        CreateMap<CreatorRequestDto, Creator>();
        
        CreateMap<Label, LabelResponseDto>();
        CreateMap<LabelRequestDto, Label>();
        
        CreateMap<Note, NoteResponseDto>();
        CreateMap<NoteRequestDto, Note>();
        
        CreateMap<News, NewsResponseDto>();
        CreateMap<NewsRequestDto, News>();
    }
}