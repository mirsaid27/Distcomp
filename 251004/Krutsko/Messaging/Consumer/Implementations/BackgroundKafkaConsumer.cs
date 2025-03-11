using Confluent.Kafka;
using Messaging.Consumer.Interfaces;
using Messaging.KafkaSerialization;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Options;

namespace Messaging.Consumer.Implementations;

public class BackgroundKafkaConsumer<TK, TV> : BackgroundService
{
    private readonly KafkaConsumerConfig _config;
    private readonly IServiceScopeFactory _serviceScopeFactory;

    public BackgroundKafkaConsumer(IOptions<KafkaConsumerConfig> config,
        IServiceScopeFactory serviceScopeFactory)
    {
        _serviceScopeFactory = serviceScopeFactory;
        _config = config.Value;
    }
    
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        using var scope = _serviceScopeFactory.CreateScope();
        var handler = scope.ServiceProvider.GetRequiredService<IKafkaHandler<TK, TV>>();

        var builder = new ConsumerBuilder<TK, TV>(_config)
            .SetValueDeserializer(new Deserializer<TV>());

        using var consumer = builder.Build();
        consumer.Subscribe(_config.Topic);

        while (!stoppingToken.IsCancellationRequested)
        {
            var result = consumer.Consume(stoppingToken);

            if (result != null)
            {
                await handler.HandleAsync(result.Message.Key, result.Message.Value);

                consumer.Commit(result);
                
                consumer.StoreOffset(result);
            }
        }
    }
}