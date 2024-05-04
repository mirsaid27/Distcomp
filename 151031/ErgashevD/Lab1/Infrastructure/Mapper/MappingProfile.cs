using AutoMapper;
using Lab1.DTO.RequestDTO;
using Lab1.DTO.ResponseDTO;
using Lab1.Models;

namespace Lab1.Infrastructure.Mapper
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<Creator, CreatorResponseDto>();
            CreateMap<CreatorRequestDto, Creator>();

            CreateMap<News, NewsResponseDto>();
            CreateMap<NewsRequestDto, News>();

            CreateMap<Sticker, StickerResponseDto>();
            CreateMap<StickerRequestDto, Sticker>();

            CreateMap<Note, NoteResponseDto>();
            CreateMap<NoteRequestDto, Note>();
        }
    }
}
