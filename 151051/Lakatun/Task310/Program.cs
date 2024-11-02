using Task310.Extensions;
using Task310.Middleware;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddApiVersioning();
builder.Services
    .AddEndpointsApiExplorer()
    .AddSwaggerGen()
    .AddServices()
    .AddRepositories()
    .AddInfrastructure();

var app = builder.Build();

app.UseGlobalErrorHandler();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

app.Run();
