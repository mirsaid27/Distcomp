using Confluent.Kafka;

namespace Messaging.Producer;

public class KafkaProducerConfig : ProducerConfig
{
    public string Topic { get; set; }

    public KafkaProducerConfig()
    {
        BootstrapServers = Environment.GetEnvironmentVariable("KAFKA_BROKER");
        AllowAutoCreateTopics = true; // !!!!!!!!!!!!!
    }
}