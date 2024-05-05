using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Npgsql;
using Publisher.Models;


namespace Publisher.Repositories
{

    public class YourDbContextFactory : IDesignTimeDbContextFactory<ApplicationContext>
    {
        public ApplicationContext CreateDbContext(string[] args)
        {
            var optionsBuilder = new DbContextOptionsBuilder<ApplicationContext>();
            var masterConnectionString = new NpgsqlConnectionStringBuilder();
            masterConnectionString.Host = "postgres-publisher-service";
            masterConnectionString.Port = 5432;
            masterConnectionString.Username = "postgres";
            masterConnectionString.Password = "postgres";
            optionsBuilder.UseNpgsql(masterConnectionString.ConnectionString);
            return new ApplicationContext(optionsBuilder.Options);
        }
    }
    public class ApplicationContext : DbContext
    {
        public DbSet<User> Users { get; set; } = null!;
        public DbSet<Tweet> Tweets { get; set; } = null!;
        public DbSet<Comment> Comments { get; set; } = null!;
        public DbSet<Sticker> Stickers { get; set; } = null!;
        public ApplicationContext(DbContextOptions<ApplicationContext> options)
        : base(options)
        { }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var masterConnectionString = new NpgsqlConnectionStringBuilder();
            masterConnectionString.Host = "postgres-publisher-service";
            masterConnectionString.Port = 5432;
            masterConnectionString.Username = "postgres";
            masterConnectionString.Password = "postgres";
            optionsBuilder.UseNpgsql(masterConnectionString.ConnectionString);
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder
                .Entity<Tweet>()
                .HasMany(c => c.Stickers)
                .WithMany(s => s.Tweets)
                .UsingEntity<TweetSticker>(
                   j => j
                    .HasOne(pt => pt.Sticker)
                    .WithMany(t => t.TweetStickers)
                    .HasForeignKey(pt => pt.stickerId),
                j => j
                    .HasOne(pt => pt.Tweet)
                    .WithMany(p => p.TweetStickers)
                    .HasForeignKey(pt => pt.tweetId),
                j =>
                {
                    j.HasKey(t => t.id);
                    j.ToTable("tbl_tweetSticker");
                });

            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(login) < 2", "LENGTH(\"login\") > 2"));
            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(password) < 8", "LENGTH(\"password\") > 8"));
            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(firstname) < 2", "LENGTH(\"firstname\") > 2"));
            modelBuilder.Entity<User>()
                .ToTable(t => t.HasCheckConstraint("len(lastname) < 2", "LENGTH(\"lastname\") > 2"));

            modelBuilder.Entity<Tweet>()
                .ToTable(t => t.HasCheckConstraint("len(title) < 2", "LENGTH(\"title\") > 2"));
            modelBuilder.Entity<Tweet>()
                .ToTable(t => t.HasCheckConstraint("len(content) < 4", "LENGTH(\"content\") > 4"));

            modelBuilder.Entity<Comment>()
                .ToTable(t => t.HasCheckConstraint("len(content) < 2", "LENGTH(\"content\") > 2"));

            modelBuilder.Entity<Sticker>()
                .ToTable(t => t.HasCheckConstraint("len(name) < 2", "LENGTH(\"name\") > 2"));

            modelBuilder.Entity<User>()
                .HasIndex(p => p.login).IsUnique();
            modelBuilder.Entity<Tweet>()
                .HasIndex(p => p.title).IsUnique();
            modelBuilder.Entity<Sticker>()
                .HasIndex(p => p.name).IsUnique();
        }

    }
}

