using dc_rest.Data;
using dc_rest.DTOs.RequestDTO;
using dc_rest.Repositories.InMemoryRepositories;
using dc_rest.Repositories.Interfaces;
using dc_rest.Repositories.PostgresRepositories;
using dc_rest.Services;
using dc_rest.Services.Interfaces;
using dc_rest.Utilities.Mapper;
using dc_rest.Utilities.Validators;
using FluentValidation;
using Microsoft.EntityFrameworkCore;
using Microsoft.OpenApi.Models;
using Swashbuckle.AspNetCore.SwaggerGen;

namespace dc_rest.Utilities.Provider;

public static class ServiceProvider
{
    public static IServiceCollection AddRepositories(this IServiceCollection services)
    {
        
        services.AddScoped<ICreatorRepository, PostgresCreatorRepository>();
        services.AddScoped<ILabelRepository, PostgresLabelRepository>();
        services.AddScoped<INewsRepository, PostgresNewsRepository>();
        //services.AddScoped<INoteRepository, PostgresNoteRepository>();
        /*services.AddSingleton<ICreatorRepository, InMemoryCreatorRepository>();
        services.AddSingleton<INewsRepository, InMemoryNewsRepository>();
        services.AddSingleton<ILabelRepository, InMemoryLabelRepository>();
        services.AddSingleton<INoteRepository, InMemoryNoteRepository>();*/

        return services;
    }

    public static IServiceCollection AddServices(this IServiceCollection services)
    {
        services.AddScoped<ICreatorService, CreatorService>();
        services.AddScoped<INewsService, NewsService>();
        services.AddScoped<ILabelService, LabelService>();
        services.AddScoped<IBaseService, BaseService>();
        services.AddSingleton<INoteService, NoteService>();

        return services;
    }

    public static IServiceCollection AddUtilities(this IServiceCollection services)
    {
        services.AddAutoMapper(typeof(MappingConfig));
        services.AddTransient<AbstractValidator<CreatorRequestDto>, CreatorValidator>();
        services.AddTransient<AbstractValidator<NewsRequestDto>, NewsValidator>();
        services.AddTransient<AbstractValidator<NoteRequestDto>, NoteValidator>();
        services.AddTransient<AbstractValidator<LabelRequestDto>, LabelValidator>();
        
        services.AddHttpClient();
        return services;
    }
    
    public class AddRequiredHeaderParameter : IOperationFilter
    {
        public void Apply(OpenApiOperation operation, OperationFilterContext context)
        {
            if (operation.Parameters == null)
                operation.Parameters = new List<OpenApiParameter>();

            operation.Parameters.Add(new OpenApiParameter()
            {
                Name = "Accept-Language",
                In = ParameterLocation.Header,
                Required = true
            });
        }
    }
}