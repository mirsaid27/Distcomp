using AutoMapper;
using Task310.DTO.RequestDTO;
using Task310.DTO.ResponseDTO;
using Task310.Models;

namespace Task310.Infrastructure.Mapper
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
