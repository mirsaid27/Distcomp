using Confluent.Kafka;

namespace Messaging.Consumer;

public class KafkaConsumerConfig : ConsumerConfig
{
    public string Topic { get; set; }

    public KafkaConsumerConfig(string topic)
    {
        Topic = topic;
        AutoOffsetReset = Confluent.Kafka.AutoOffsetReset.Earliest;
        EnableAutoOffsetStore = false;
    }
}