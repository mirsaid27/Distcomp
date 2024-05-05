using Publisher.Entity.DTO.RequestTO;
using Publisher.Kafka.Model;

namespace Publisher.Service.Interface
{
    public interface IMessageService
    {
        Task<IList<MessageKafkaResponse>> GetAll();
        Task<MessageKafkaResponse?> Add(MessageRequestTO requestTo);
        Task<bool> Remove(int id);
        Task<MessageKafkaResponse?> Update(MessageRequestTO requestTo);
        Task<MessageKafkaResponse?> GetByID(int id);
    }
}
