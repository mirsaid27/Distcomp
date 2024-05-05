using AutoMapper;
using Discussion.Common;
using Discussion.MessageEntity.Dto;
using Discussion.MessageEntity.Interface;

namespace Discussion.MessageEntity
{
    public class MessageService(IMapper mapper, IMessageRepository repository)
        : AbstractCrudService<Message, MessageRequestTO, MessageResponseTO>(mapper, repository), IMessageService
    {
        public override async Task<MessageResponseTO> Add(MessageRequestTO message)
        {
            if (!Validate(message))
            {
                throw new InvalidDataException("Message is not valid");
            }

            return await base.Add(message);
        }

        public override async Task<MessageResponseTO> Update(MessageRequestTO message)
        {
            if (!Validate(message))
            {
                throw new InvalidDataException($"UPDATE invalid data: {message}");
            }

            return await base.Update(message);
        }

        public Task<IList<Message>> GetByIssueID(int issueId)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(MessageRequestTO message)
        {
            var contentLen = message.Content.Length;

            if (contentLen < 2 || contentLen > 2048)
            {
                return false;
            }
            return true;
        }
    }
}
