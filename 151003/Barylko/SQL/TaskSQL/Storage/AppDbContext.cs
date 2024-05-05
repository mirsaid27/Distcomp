using Microsoft.EntityFrameworkCore;
using TaskREST.Models;

namespace TaskREST.Storage;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
        //Database.EnsureDeleted();
        Database.EnsureCreated();
    }

    public DbSet<User> tbl_user { get; set; } = null!;
    public DbSet<Tag> tbl_tag { get; set; } = null!;
    public DbSet<Issue> tbl_issue { get; set; } = null!;
    public DbSet<Comment> tbl_comment { get; set; } = null!;
}