using FluentValidation;
using Task310.Infrastructure.Mapper;
using Task310.Infrastructure.Validators;
using Task310.Repositories.Implementations;
using Task310.Repositories.Interfaces;
using Task310.Services.Impementations;
using Task310.Services.Interfaces;

namespace Task310.Extensions
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
