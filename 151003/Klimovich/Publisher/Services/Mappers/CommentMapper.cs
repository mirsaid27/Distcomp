using AutoMapper;
using Publisher.Models;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;

namespace Publisher.Services.Mappers
{
    public class CommentMapper : Profile
    {
        public CommentMapper()
        {
            CreateMap<Comment, CommentRequestTo>().ReverseMap();
            CreateMap<Comment, CommentResponceTo>().ReverseMap();

        }
    }
}
