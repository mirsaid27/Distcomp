using System.Text.Json.Serialization;
using Discussion.DTO.Request;
using Discussion.DTO.Response;
using Discussion.Extensions;
using Discussion.KafkaConsumers;
using Discussion.Middleware;
using Messaging;
using Messaging.Extensions;
using Scalar.AspNetCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers()
    .AddJsonOptions(options =>
    {
        options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
    });

// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddCassandra(builder.Configuration)
    .AddServices()
    .AddRepositories()
    .AddInfrastructure()
    .AddKafkaProducer<string, KafkaMessage<NoticeResponseDTO>>(options =>
    {
        options.Topic = "OutTopic";
    })
    .AddKafkaConsumer<string, KafkaMessage<NoticeRequestDTO>, InTopicHandler>(options =>
    {
        options.Topic = "InTopic";
    });

var app = builder.Build();

app.Map("/", () => "Hello, World!");

// Middleware для глобальных ошибок
app.UseMiddleware<GlobalExceptionMiddleware>();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
    app.MapScalarApiReference(options =>
    {
        options.WithTheme(ScalarTheme.DeepSpace);
    });
}

app.UseAuthorization();

app.MapControllers();

app.Run();