using DistComp.Repositories.Implementations;
using DistComp.Repositories.Interfaces;
using DistComp.Services.Implementations;
using DistComp.Services.Interfaces;

namespace DistComp.Extensions;

public static class ServiceExtensions
{
    public static IServiceCollection AddRepositories(this IServiceCollection services)
    {
        services.AddSingleton<IUserRepository, InMemoryUserRepository>();
        services.AddSingleton<IStoryRepository, InMemoryStoryRepository>();
        services.AddSingleton<ITagRepository, InMemoryTagRepository>();
        services.AddSingleton<INoticeRepository, InMemoryNoticeRepository>();

        return services;
    }

    public static IServiceCollection AddServices(this IServiceCollection services)
    {
        services.AddScoped<IUserService, UserService>();
        services.AddScoped<IStoryService, StoryService>();
        services.AddScoped<ITagService, TagService>();
        services.AddScoped<INoticeService, NoticeService>();
        
        return services;
    }
}