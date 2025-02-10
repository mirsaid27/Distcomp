using System.Reflection;
using Service.Interfaces;
using Service.Services;
using Domain.Repository;
using Infrastructure.Repositories;
using Service.Mapper;

namespace Presentation.Extensions;

public static class ServiceExtensions
{
    public static void ConfigureServiceLayer(this IServiceCollection services)
    {
        services.AddSingleton<IEditorService, EditorService>();
        services.AddSingleton<IArticleService, ArticleService>();
        services.AddSingleton<IPostService, PostService>();
        services.AddSingleton<IMarkService, MarkService>();
        services.AddAutoMapper(Assembly.GetExecutingAssembly());
    }
    
    public static void ConfigureInfrastructureLayer(this IServiceCollection services)
    {
        services.AddSingleton<IEditorRepository, EditorRepository>();
        services.AddSingleton<IMarkRepository, MarkRepository>();
        services.AddSingleton<IArticleRepository, ArticleRepository>();
        services.AddSingleton<IPostRepository, PostRepository>();
    }
    
    public static void ConfigureAutoMapper(this IServiceCollection services)
    {
        services.AddAutoMapper(cfg =>
        {
            cfg.AddProfile<ArticleMappingProfile>();
            cfg.AddProfile<EditorMappingProfile>();
            cfg.AddProfile<MarkMappingProfile>();
            cfg.AddProfile<PostMappingProfile>();
        }, AppDomain.CurrentDomain.GetAssemblies());
    }
}