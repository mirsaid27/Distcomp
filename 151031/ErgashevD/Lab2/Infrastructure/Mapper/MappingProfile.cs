using AutoMapper;
using Lab2.DTO.RequestDTO;
using Lab2.DTO.ResponseDTO;
using Lab2.Models;

namespace Lab2.Infrastructure.Mapper
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
