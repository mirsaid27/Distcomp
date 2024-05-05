using Discussion.Models;
using Discussion.Repositories;
using Discussion.Services;
using Discussion.Services.Interfaces;
using Discussion.Services.Kafka;
using Discussion.Services.Mappers;
using Discussion.Services.Realisation;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddTransient<IRepository<Comment>, CommentRepository>();
builder.Services.AddTransient<ICommentService, CommentService>();
builder.Services.AddTransient<IServiceBase, ServiceBase>();
builder.Services.AddTransient<IKafkaService, KafkaService>();

builder.Services.AddAutoMapper(typeof(CommentMapper));

builder.Services.AddControllers();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

var kafka = app.Services.GetService<IKafkaService>();
Thread kafkaThread = new Thread(kafka.StartConsuming);
kafkaThread.Start();

app.Run();
