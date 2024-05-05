using System.Reflection;
using dc_rest.Models;
using Microsoft.EntityFrameworkCore;

namespace dc_rest.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
        
    }

    public AppDbContext()
    {
        
    }
    public virtual DbSet<Creator> Creators { get; set; }
    public virtual DbSet<Label> Labels { get; set; }
    public virtual DbSet<News> News { get; set; }
    public virtual DbSet<Note> Notes { get; set; }
    
    public virtual DbSet<NewsLabel> NewLabel { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());
    }
}