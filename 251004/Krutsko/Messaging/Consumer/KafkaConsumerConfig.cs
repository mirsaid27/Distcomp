using Confluent.Kafka;

namespace Messaging.Consumer;

public class KafkaConsumerConfig : ConsumerConfig
{
    public string Topic { get; set; }

    public KafkaConsumerConfig()
    {
        AutoOffsetReset = Confluent.Kafka.AutoOffsetReset.Earliest;
        EnableAutoOffsetStore = false;
        GroupId = "my-consumer-group";
        BootstrapServers = Environment.GetEnvironmentVariable("KAFKA_BROKER");
        AllowAutoCreateTopics = true; ///!!!!!!!!!!!!!!
    }
}