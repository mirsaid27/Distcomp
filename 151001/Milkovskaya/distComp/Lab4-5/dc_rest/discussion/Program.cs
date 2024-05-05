using Confluent.Kafka;
using discussion.Infrastructure.Kafka;
using discussion.Middleware;
using discussion.Utilities;
using Microsoft.AspNetCore.Mvc;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(o=>o.OperationFilter<DependencyInjection.AddRequiredHeaderParameter>());



builder.Services.Configure<ProducerConfig>(builder.Configuration.GetRequiredSection("Kafka:Producer"));
builder.Services.Configure<ConsumerConfig>(builder.Configuration.GetRequiredSection("Kafka:Consumer"));
builder.Services.AddSingleton<MessageProcessor>();
builder.Services.AddHostedService<ConsumerService>();

builder.Services.AddControllers();
builder.Services.ConfigureServices();

builder.Services.AddApiVersioning(config =>
{
    config.DefaultApiVersion = new ApiVersion(1, 0);
    config.AssumeDefaultVersionWhenUnspecified = true;
    config.ReportApiVersions = true;
});

builder.Services.AddExceptionHandler<GlobalExceptionHandler>();

var app = builder.Build();
app.UseExceptionHandler(_=> { });
// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.MapControllers();

app.Run();

