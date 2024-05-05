using AutoMapper;
using discussion.Models;

namespace discussion.Utilities.Mapper;

public class MappingConfig : Profile
{
    public MappingConfig()
    {
        CreateMap<Note, NoteResponseDto>();
        CreateMap<NoteRequestDto, Note>();
    }
}