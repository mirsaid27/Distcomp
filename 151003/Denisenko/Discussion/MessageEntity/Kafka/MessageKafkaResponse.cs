using Discussion.Kafka;
using Discussion.MessageEntity.Dto;

namespace Discussion.MessageEntity.Kafka
{
    public class MessageKafkaResponse(MessageResponseTO responseTO, State state)
    {
        public MessageResponseTO ResponseTO { get; set; } = responseTO;
        public State State { get; set; } = state;
    }
}
