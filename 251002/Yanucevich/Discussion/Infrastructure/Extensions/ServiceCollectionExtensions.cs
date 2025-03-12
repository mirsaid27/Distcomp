using System;
using Domain.Repositories;
using Infrastructure.Repositories.Mongo;
using Infrastructure.Settings;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Infrastructure.Extensions;

public static class ServiceCollectionExtensions
{
    public static IServiceCollection AddRepositories(this IServiceCollection services)
    {
        // services.AddSingleton<IMarkerRepository, MarkerRepositoryInMemory>();
        // services.AddSingleton<IUserRepository, UserRepositoryInMemory>();
        // services.AddSingleton<ITweetRepository, TweetRepositoryInMemory>();
        // services.AddSingleton<IReactionRepository, ReactionRepositoryInMemory>();

        services.AddSingleton<IReactionRepository, ReactionRepositoryMongo>();

        return services;
    }

    public static IServiceCollection AddMongoInfrastructure(
        this IServiceCollection services,
        IConfigurationRoot config
    )
    {
        services.Configure<MongoOptions>(config.GetSection("ReactionDatabase"));
        return services;
    }
}
