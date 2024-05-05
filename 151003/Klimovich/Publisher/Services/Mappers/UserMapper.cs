using AutoMapper;
using Publisher.Models.DTO.DTOs;
using Publisher.Models.DTO.ResponseTo;
using Publisher.Models;

namespace Publisher.Services.Mappers
{
    public class UserMapper : Profile
    {
        public UserMapper()
        {
            CreateMap<User, UserResponceTo>().ReverseMap();
            CreateMap<User, UserRequestTo>().ReverseMap();
        }
    }
}
