using Publisher.Repositories;
using Npgsql;
using Microsoft.EntityFrameworkCore;
using Publisher.Models;
using Publisher.Services.Interfaces;
using Publisher.Services.Realisation;
using Publisher.Services;
using Publisher.Services.Mappers;
using Publisher;
using Publisher.Services.Remote;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

var masterConnectionString = new NpgsqlConnectionStringBuilder();
masterConnectionString.Host = "postgres-publisher-service";
masterConnectionString.Port = 5432;
masterConnectionString.Username = "postgres";
masterConnectionString.Password = "postgres";
using (var connection = new NpgsqlConnection(masterConnectionString.ConnectionString))
{
    connection.Open();
    using (var command = new NpgsqlCommand())
    {
        command.Connection = connection;
        command.CommandText = $"SELECT 1 FROM pg_database WHERE datname='distcomp'";
        var result = command.ExecuteScalar();
        if (!(result != null && result != DBNull.Value))
        {
            command.CommandText = $"CREATE DATABASE \"distcomp\"";
            command.ExecuteNonQuery();
            command.CommandText = $"GRANT ALL PRIVILEGES ON DATABASE \"distcomp\" TO \"postgres\"";
            command.ExecuteNonQuery();
        }
    }
}
string connectionString = masterConnectionString.ConnectionString;
var dbContextOptions = new DbContextOptionsBuilder<ApplicationContext>()
                .UseNpgsql(connectionString).Options;
var _context = new ApplicationContext(dbContextOptions);
_context.Database.Migrate();
_context.Dispose();
/*string connection = builder.Configuration.GetConnectionString("Host=localhost;" +
    "Port=5432;" +
    "Database=distcomp;" +
    "Username=postgres;" +
    "Password=postgres"
    );
builder.Services.AddDbContext<ApplicationContext>(options => options.UseNpgsql(connection));*/


//string connectionString = masterConnectionString.ConnectionString;
builder.Services.AddDbContext<ApplicationContext>(options => options.UseNpgsql(connectionString));

builder.Services.AddTransient<IRepository<User>, UserRepository>();
builder.Services.AddTransient<IRepository<Tweet>, TweetRepository>();
builder.Services.AddTransient<IRepository<Comment>, CommentRepository>();
builder.Services.AddTransient<IRepository<Sticker>, StickerRepository>();

builder.Services.AddTransient<IUserService, UserService>();
builder.Services.AddTransient<ITweetService, TweetService>();
builder.Services.AddTransient<ICommentService, CommentServiceKafka>();
builder.Services.AddTransient<IStickerService, StickerService>();

builder.Services.AddTransient<IServiceBase, ServiceBase>();

builder.Services.AddAutoMapper(typeof(UserMapper));
builder.Services.AddAutoMapper(typeof(TweetMapper));
builder.Services.AddAutoMapper(typeof(CommentMapper));
builder.Services.AddAutoMapper(typeof(StickerMapper));

builder.Services.AddStackExchangeRedisCache(options => {
    options.Configuration = "redis:6379";
    options.InstanceName = "local";
});

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
    //app.ApplyMigration();
}

//app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
