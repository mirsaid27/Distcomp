using Confluent.Kafka;

namespace Lab4.Messaging.Producer;

public class KafkaProducerConfig<Tk, Tv> : ProducerConfig
{
    public string Topic { get; set; }
}