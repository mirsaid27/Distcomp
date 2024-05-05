using AutoMapper;
using DC.Models.DTOs.ResponceTo;
using Publisher.Models;
using Publisher.Models.DTOs.DTO;

namespace Publisher.Services.Mappers
{
    public sealed class StickerMapper : Profile
    {
        public StickerMapper()
        {
            CreateMap<Sticker, StickerRequestTo>().ReverseMap();
            CreateMap<StickerResponceTo, Sticker>().ReverseMap();
        }
    }
}
