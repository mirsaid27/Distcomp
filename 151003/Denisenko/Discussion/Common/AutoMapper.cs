using AutoMapper;
using Discussion.MessageEntity;
using Discussion.MessageEntity.Dto;

namespace Discussion.Common
{
    public class AutoMapper : Profile
    {
        public AutoMapper()
        {
            CreateMap<MessageRequestTO, Message>();
            CreateMap<Message, MessageResponseTO>();
        }
    }
}
