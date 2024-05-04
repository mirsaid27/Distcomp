using Lab4.Messaging.Consumer;
using Lab4.Messaging;
using Lab5.Publisher.DTO.ResponseDTO;

namespace Lab5.Publisher.Consumers
{
    public class OutTopicHandler : IKafkaHandler<string, KafkaMessage<NoteResponseDto>>
    {
        public async Task HandleAsync(string key, KafkaMessage<NoteResponseDto> value)
        {
        }
    }
}
