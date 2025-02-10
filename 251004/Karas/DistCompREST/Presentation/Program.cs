using Presentation.Extensions;

var builder = WebApplication.CreateBuilder(args);
{
    builder.Services.ConfigureServiceLayer();
    builder.Services.ConfigureInfrastructureLayer();
    builder.Services.ConfigureAutoMapper();
    
    builder.Services.AddControllers();

    builder.Services.AddSwaggerGen();
    builder.Services.AddEndpointsApiExplorer();
}

var app = builder.Build();
{
    app.UseExceptionHandler("/errors");
    
    app.UseSwagger();
    app.UseSwaggerUI();
    
    app.UseHttpsRedirection();
    app.MapControllers();
}
app.Run();