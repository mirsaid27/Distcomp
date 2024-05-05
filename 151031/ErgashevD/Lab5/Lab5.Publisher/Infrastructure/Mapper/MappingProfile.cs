using AutoMapper;
using Lab5.Publisher.DTO.RequestDTO;
using Lab5.Publisher.DTO.ResponseDTO;
using Lab5.Publisher.Models;

namespace Lab5.Publisher.Infrastructure.Mapper;

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