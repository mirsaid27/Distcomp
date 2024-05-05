using Cassandra;
using Cassandra.Mapping;
using discussion.Models;
using discussion.Repositories;
using discussion.Repositories.Interfaces;
using discussion.Services;
using discussion.Services.Interfaces;
using discussion.Utilities.Mapper;
using discussion.Utilities.Validators;
using FluentValidation;
using Microsoft.OpenApi.Models;
using Swashbuckle.AspNetCore.SwaggerGen;

namespace discussion.Utilities;

public static class DependencyInjection
{
    public static IServiceCollection ConfigureServices(this IServiceCollection services)
    {
        services.AddTransient<INoteService, NoteService>();
        services.AddTransient<INoteRepository, NoteRepository>();
        var cluster = Cluster.Builder().AddContactPoint("127.0.0.1").Build();
        var session = cluster.Connect("distcomp");
        var mappingConfig = new MappingConfiguration().Define(new CassandraMappings());
        var mapper = new Cassandra.Mapping.Mapper(session, mappingConfig);
        services.AddSingleton<IMapper>(mapper);
        services.AddAutoMapper(typeof(MappingConfig));
        services.AddTransient<AbstractValidator<NoteRequestDto>, NoteValidator>();
        
        
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
                Required = false
            });
        }
    }
}