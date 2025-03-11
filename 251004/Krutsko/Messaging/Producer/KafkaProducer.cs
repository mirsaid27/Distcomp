using Confluent.Kafka;
using Microsoft.Extensions.Options;

namespace Messaging.Producer;

public class KafkaProducer<TK, TV> : IDisposable
{
    private readonly IProducer<TK, TV> _producer;
    private readonly KafkaProducerConfig<TK, TV> _config;

    public KafkaProducer(IOptions<KafkaProducerConfig<TK, TV>> topicOptions,
        IProducer<TK, TV> producer)
    {
        _config = topicOptions.Value;
        _producer = producer;
    }
    
    public async Task ProduceAsync(TK key, TV value)
    {
        await _producer.ProduceAsync(_config.Topic, 
            new Message<TK, TV> { Key = key, Value = value });
    }

    public void Dispose()
    {
        _producer.Dispose();
    }
}