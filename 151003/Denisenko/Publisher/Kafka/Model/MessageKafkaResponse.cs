using Publisher.Entity.DTO.ResponseTO;

namespace Publisher.Kafka.Model
{
    public class MessageKafkaResponse(MessageResponseTO responseTO, State state)
    {
        public MessageResponseTO ResponseTO { get; set; } = responseTO;
        public State State { get; set; } = state;
    }
}
