using Discussion.Common.Interface;
using Discussion.MessageEntity.Dto;

namespace Discussion.MessageEntity.Interface
{
    public interface IMessageService : ICrudService<Message, MessageRequestTO, MessageResponseTO>
    {
        Task<IList<Message>> GetByIssueID(int issueId);
    }
}
