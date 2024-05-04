using AutoMapper;
using Lab3.Publisher.DTO.RequestDTO;
using Lab3.Publisher.DTO.ResponseDTO;
using Lab3.Publisher.Models;

namespace Lab3.Publisher.Infrastructure.Mapper;

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