using Confluent.Kafka;

namespace Messaging.Consumer.Implementations;

public class KafkaConsumerConfig<TK, TV> : ConsumerConfig
{
    public string Topic { get; set; }

    public KafkaConsumerConfig()
    {
        AutoOffsetReset = Confluent.Kafka.AutoOffsetReset.Earliest;
        EnableAutoOffsetStore = false;
    }
}