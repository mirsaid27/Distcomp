using AutoMapper;
using Lab3.Discussion.DTO.RequestDTO;
using Lab3.Discussion.DTO.ResponseDTO;
using Lab3.Discussion.Models;

namespace Lab3.Discussion.Infrastructure.Mapper
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
