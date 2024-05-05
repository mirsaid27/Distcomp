using Google.Protobuf.WellKnownTypes;
using Microsoft.EntityFrameworkCore;
using Publisher.Models;
namespace Publisher.Storage;


public class AppDbContext(DbContextOptions<AppDbContext> options) : DbContext(options)
{
    public DbSet<User> Users { get; set; } = null!;
    public DbSet<Tag> Tags { get; set; } = null!;
    public DbSet<Issue> Issues { get; set; } = null!;
}