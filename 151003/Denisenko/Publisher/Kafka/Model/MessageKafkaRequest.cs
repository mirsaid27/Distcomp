using Publisher.Entity.DTO.RequestTO;

namespace Publisher.Kafka.Model
{
    public class MessageKafkaRequest(MessageRequestTO requestTO, HttpMethod method)
    {
        public MessageRequestTO RequestTO { get; set; } = requestTO;
        public HttpMethod Method { get; set; } = method;
    }
}
