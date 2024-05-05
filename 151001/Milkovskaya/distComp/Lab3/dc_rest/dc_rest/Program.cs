using dc_rest.Data;
using dc_rest.Middleware;
using dc_rest.Utilities.Provider;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ServiceProvider = dc_rest.Utilities.Provider.ServiceProvider;

var builder = WebApplication.CreateBuilder(args);


builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(o=>o.OperationFilter<ServiceProvider.AddRequiredHeaderParameter>());

builder.Services.AddExceptionHandler<GlobalExceptionHandler>();


builder.Services.AddControllers();

builder.Services.AddApiVersioning(config =>
{
    config.DefaultApiVersion = new ApiVersion(1, 0);
    config.AssumeDefaultVersionWhenUnspecified = true;
    config.ReportApiVersions = true;
});

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));
var options = new DbContextOptionsBuilder<AppDbContext>()
    .UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"))
    .Options;

using (var context = new AppDbContext(options))
{
    context.Database.EnsureDeleted();
    context.Database.EnsureCreated();
}

builder.Services
    .AddEndpointsApiExplorer()
    .AddSwaggerGen()
    .AddServices()
    .AddRepositories()
    .AddUtilities();



var app = builder.Build();
app.UseExceptionHandler(_=> { });
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.MapControllers();

app.Run();

