using AutoMapper;
using Discussion.Models;
using Discussion.Models.DTO.RequestTo;
using Discussion.Models.DTO.ResponceTo;

namespace Discussion.Services.Mappers
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
