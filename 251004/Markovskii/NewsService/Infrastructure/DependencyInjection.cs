using Domain.Repository;
using Infrastructure.Repositories.InMemoryRepositories;
using Infrastructure.Repositories.PostgersRepositories;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddRepositories(this IServiceCollection services){


        services.AddSingleton<IMarkRepository, PgMarkRepository>();
        services.AddSingleton<IEditorRepository, PgEditorRepository>();
        services.AddSingleton<IPostRepository, PgPostRepository>();
        services.AddSingleton<INewsRepository, PgNewsRepository>(); 

        return services;
    }

    public static IServiceCollection AddInfrastructure (
        this IServiceCollection services,
        IConfigurationRoot config
    ){
        
        services.Configure<InfrastructureOptions>(options => {
            options.PostgresConnectionString = config.GetConnectionString("npg");
        });
            
        Postgres.MapCompositeTypes();   
        Postgres.AddMigrations(services);
                
        return services;
    }
}