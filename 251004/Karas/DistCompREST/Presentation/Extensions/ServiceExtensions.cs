using Application.Contracts.RepositoryContracts;
using Application.Contracts.ServiceContracts;
using Application.Services;
using Infrastructure.Repositories;
using Infrastructure.Repositories.Implementations;
using Microsoft.EntityFrameworkCore;

namespace Presentation.Extensions;

public static class ServiceExtensions
{
    public static void AddRepositories(this IServiceCollection services)
    {
        services.AddScoped<IEditorRepository, EditorRepository>();
        services.AddScoped<IArticleRepository, ArticleRepository>();
        services.AddScoped<IMarkRepository, MarkRepository>();
        services.AddScoped<IPostRepository, PostRepository>();
    }

    public static void AddServices(this IServiceCollection services)
    {
        services.AddScoped<IEditorService, EditorService>();
        services.AddScoped<IArticleService, ArticleService>();
        services.AddScoped<IMarkService, MarkService>();
        services.AddScoped<IPostService, PostService>();
    }

    public static void AddDbContext(this IServiceCollection services, IConfigurationManager config)
    {
        services.AddDbContext<RepositoryContext>(opts =>
            opts.UseNpgsql(config.GetConnectionString("PostgresConnection"), b =>
                b.MigrationsAssembly("Presentation")));
    }
}