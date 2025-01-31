using Application.Behaviors;
using Application.Extensions;
using FluentValidation;
using Infrastructure.Extensions;
using MediatR;

var builder = WebApplication.CreateBuilder(args);


builder.Logging.ClearProviders();
builder.Logging.AddConsole();
builder.Services
    .AddApplicationServices()
    .AddValidationServices()
    .AddRepositories();

builder.Services.AddControllers();

var app = builder.Build();

app.MapControllers();

app.Run();
