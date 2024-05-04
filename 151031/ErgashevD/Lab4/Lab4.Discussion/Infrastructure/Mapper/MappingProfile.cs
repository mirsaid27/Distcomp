using AutoMapper;
using Lab4.Discussion.DTO.RequestDTO;
using Lab4.Discussion.DTO.ResponseDTO;
using Lab4.Discussion.Models;

namespace Lab4.Discussion.Infrastructure.Mapper
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<Note, NoteResponseDto>();
            CreateMap<NoteRequestDto, Note>();
        }
    }
}
