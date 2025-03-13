using Domain.Repositories;
using Infrastructure.Repositories.MongoDb;
using Infrastructure.Settings;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddRepositories(this IServiceCollection services){


        services.AddSingleton<IPostRepository, PostMongoDbRepository>();

        return services;
    }
    
    public static IServiceCollection AddInfrastructure (
        this IServiceCollection services,
        IConfigurationRoot config
    ){
        
        services.Configure<MongoDbOptions>(config.GetSection("PostDatabase"));
        return services;
    }
}