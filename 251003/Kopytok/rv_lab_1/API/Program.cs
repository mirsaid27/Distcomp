using API.Kafka;
using Application.abstractions;
using Application.services;
using Application.Services;
using Database;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);


builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddScoped<IEditorService, EditorService>();
builder.Services.AddScoped<IStoryService, StoryService>();
builder.Services.AddScoped<ITagService, TagService>();
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddSingleton<NoteMessageProducer>();
builder.Services.AddSingleton<NoteResponseListener>();
builder.Services.AddHostedService<NoteMessageConsumer>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();