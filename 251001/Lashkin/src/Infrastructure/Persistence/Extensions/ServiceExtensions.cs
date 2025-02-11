using Domain.Interfaces;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Persistence.Repositories;

namespace Persistence.Extensions;

public static class ServiceExtensions
{
    public static IServiceCollection ConfigureRepositories(this IServiceCollection services)
    {
        services.AddDbContext<RepositoryContext>(options => options.UseInMemoryDatabase("DistcompDB"));
        services.AddScoped<IUnitOfWork, UnitOfWork>();
        
        return services;
    }
}