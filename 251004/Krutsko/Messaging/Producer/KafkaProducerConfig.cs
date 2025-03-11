using Confluent.Kafka;

namespace Messaging.Producer;

public class KafkaProducerConfig<TK, TV> : ProducerConfig
{
    public string Topic { get; set; }
}