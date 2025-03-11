using Messaging.MessageBus.Interfaces;
using Messaging.Producer;
using Messaging.Producer.Implementations;

namespace Messaging.MessageBus.Implementations;

public class KafkaMessageBus<TK, TV> : IMessageBus<TK, TV>
{
    public KafkaProducer<TK, TV> Producer { get; }
    public KafkaMessageBus(KafkaProducer<TK, TV> producer)
    {
        Producer = producer;
    }
    
    public async Task PublishAsync(TK key, TV message)
    {
        await Producer.ProduceAsync(key, message);
    }
}