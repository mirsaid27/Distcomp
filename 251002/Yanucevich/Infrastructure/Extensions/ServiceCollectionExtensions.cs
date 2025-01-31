using System;
using Domain.Repositories;
using Infrastructure.Repositories.InMemory;
using Microsoft.Extensions.DependencyInjection;

namespace Infrastructure.Extensions;

public static class ServiceCollectionExtensions
{
    public static IServiceCollection AddRepositories(this IServiceCollection services){
        services.AddSingleton<IMarkerRepository, MarkerRepositoryInMemory>();
        services.AddSingleton<IUserRepository, UserRepositoryInMemory>();
        services.AddSingleton<ITweetRepository, TweetRepositoryInMemory>();
        services.AddSingleton<IReactionRepository, ReactionRepositoryInMemory>();

        return services;
    }
}
