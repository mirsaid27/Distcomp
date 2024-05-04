using AutoMapper;
using Lab4.Publisher.DTO.RequestDTO;
using Lab4.Publisher.DTO.ResponseDTO;
using Lab4.Publisher.Models;

namespace Lab4.Publisher.Infrastructure.Mapper;

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
    }
}