using System.Text.Json.Serialization;
using Application.Mapper;
using Application.Validation;
using FluentValidation;
using Presentation.Extensions;
using Presentation.Middleware;

var builder = WebApplication.CreateBuilder(args);

builder.Services
    .AddControllers()
    .AddJsonOptions(options =>
    {
        options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
    });

builder.Services.AddEndpointsApiExplorer();

builder.Services.AddAutoMapper(typeof(MappingProfile));
builder.Services.AddValidatorsFromAssemblyContaining<EditorRequestDtoValidator>();

builder.Services.AddRepositories();
builder.Services.AddServices();
builder.Services.AddDbContext(builder.Configuration);

var app = builder.Build();

app.UseMiddleware<GlobalExceptionMiddleware>();

app.UseAuthorization();

app.MapControllers();

app.Run();