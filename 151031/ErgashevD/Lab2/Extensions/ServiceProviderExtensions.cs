using FluentValidation;
using Lab2.Data;
using Lab2.Infrastructure.Mapper;
using Lab2.Infrastructure.Validators;
using Lab2.Repositories.Implementations;
using Lab2.Repositories.Interfaces;
using Lab2.Services.Impementations;
using Lab2.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace Lab2.Extensions
{
    public static class ServiceProviderExtensions
    {
        public static IServiceCollection AddDbContext(this IServiceCollection services, IConfigurationManager config)
        {
            services.AddDbContext<AppDbContext>(options =>
                options
                .UseLazyLoadingProxies()
                .UseNpgsql(config.GetConnectionString("PostgresConnection")));
            return services;
        }

        public static IServiceCollection AddRepositories(this IServiceCollection services)
        {
            services.AddScoped<ICreatorRepository, CreatorSqlRepository>();
            services.AddScoped<INewsRepository, NewsSqlRepository>();
            services.AddScoped<IStickerRepository, StickerSqlRepository>();
            services.AddScoped<INoteRepository, NoteSqlRepository>();

            return services;
        }

        public static IServiceCollection AddServices(this IServiceCollection services)
        {
            services.AddScoped<ICreatorService, CreatorService>();
            services.AddScoped<INewsService, NewsService>();
            services.AddScoped<IStickerService, StickerService>();
            services.AddScoped<INoteService, NoteService>();

            return services;
        }

        public static IServiceCollection AddInfrastructure(this IServiceCollection services)
        {
            services.AddAutoMapper(typeof(MappingProfile));
            services.AddValidatorsFromAssemblyContaining<CreatorRequestDtoValidator>();

            return services;
        }
    }
}
