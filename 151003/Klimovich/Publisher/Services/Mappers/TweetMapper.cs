using AutoMapper;
using Publisher.Models;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Models;

namespace Publisher.Services.Mappers
{
    public class TweetMapper : Profile
    {
        public TweetMapper() 
        {
            CreateMap<Tweet, TweetRequestTo>().ReverseMap();
            CreateMap<Tweet, TweetResponceTo>().ReverseMap();
        }

    }
}
