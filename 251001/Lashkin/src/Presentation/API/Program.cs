using API.Middlewares;
using Application.Extensions;
using Persistence.Extensions;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddTransient<ExceptionHandlingMiddleware>();
builder.Services.ConfigureRepositories();
builder.Services.ConfigureAutoMapper();
builder.Services.ConfigureValidation();
builder.Services.ConfigureMediatR();

var app = builder.Build();

app.UseMiddleware<ExceptionHandlingMiddleware>();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.Run();
