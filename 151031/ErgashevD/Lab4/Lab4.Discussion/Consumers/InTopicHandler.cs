using Lab4.Messaging.Consumer;
using Lab4.Messaging;
using Lab4.Discussion.DTO.RequestDTO;

namespace Lab4.Discussion.Consumers
{
    public class InTopicHandler : IKafkaHandler<string, KafkaMessage<NoteRequestDto>>
    {
        public async Task HandleAsync(string key, KafkaMessage<NoteRequestDto> value)
        {
        }
    }
}
