using Discussion.Kafka;
using Discussion.MessageEntity.Dto;

namespace Discussion.MessageEntity.Kafka
{
    public class MessageKafkaRequest(MessageRequestTO requestTO, HttpMethod method)
    {
        public MessageRequestTO RequestTO { get; set; } = requestTO;
        public HttpMethod Method { get; set; } = method;
    }
}
