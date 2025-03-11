using Confluent.Kafka;
using Messaging.Consumer.Implementations;
using Messaging.Consumer.Interfaces;
using Messaging.KafkaSerialization;
using Messaging.MessageBus.Implementations;
using Messaging.MessageBus.Interfaces;
using Messaging.Producer;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;

namespace Messaging.Extensions;

public static class KafkaExtensions
{
    public static IServiceCollection AddKafkaMessageBus(this IServiceCollection serviceCollection)
    {
        return serviceCollection.AddSingleton(
            typeof(IMessageBus<,>), 
            typeof(KafkaMessageBus<,>));
    }
    
    public static IServiceCollection AddKafkaConsumer<TK, TV, THandler>(this IServiceCollection services,
        Action<KafkaConsumerConfig<TK, TV>> configAction) where THandler : class, IKafkaHandler<TK, TV>
    {
        services.AddScoped<IKafkaHandler<TK, TV>, THandler>();

        services.AddHostedService<BackgroundKafkaConsumer<TK, TV>>();

        services.Configure(configAction);

        return services;
    }

    public static IServiceCollection AddKafkaProducer<TK, TV>(this IServiceCollection services,
        Action<KafkaProducerConfig<TK, TV>> configAction)
    {
        services.AddSingleton(
            sp =>
            {
                var config = sp.GetRequiredService<IOptions<KafkaProducerConfig<TK, TV>>>();

                var builder = new ProducerBuilder<TK, TV>(config.Value)
                    .SetValueSerializer(new Serializer<TV>());

                return builder.Build();
            });

        services.AddSingleton<KafkaProducer<TK, TV>>();

        services.Configure(configAction);

        return services;
    }
}