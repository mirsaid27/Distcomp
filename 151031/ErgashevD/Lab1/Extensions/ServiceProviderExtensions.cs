using FluentValidation;
using Lab1.Infrastructure.Mapper;
using Lab1.Infrastructure.Validators;
using Lab1.Repositories.Implementations;
using Lab1.Repositories.Interfaces;
using Lab1.Services.Impementations;
using Lab1.Services.Interfaces;

namespace Lab1.Extensions
{
    public static class ServiceProviderExtensions
    {
        public static IServiceCollection AddRepositories(this IServiceCollection services)
        {
            services.AddSingleton<ICreatorRepository, InMemoryCreatorRepository>();
            services.AddSingleton<INewsRepository, InMemoryNewsRepository>();
            services.AddSingleton<IStickerRepository, InMemoryStickerRepository>();
            services.AddSingleton<INoteRepository, InMemoryNoteRepository>();

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
