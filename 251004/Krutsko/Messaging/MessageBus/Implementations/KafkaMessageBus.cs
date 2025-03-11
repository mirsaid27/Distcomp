using Messaging.MessageBus.Interfaces;
using Messaging.Producer;

namespace Messaging.MessageBus.Implementations;

public class KafkaMessageBus<TK, TV> : IMessageBus<TK, TV>
{
    public readonly KafkaProducer<TK, TV> Producer;
    public KafkaMessageBus(KafkaProducer<TK, TV> producer)
    {
        Producer = producer;
    }
    
    public async Task PublishAsync(TK key, TV message)
    {
        await Producer.ProduceAsync(key, message);
    }
}