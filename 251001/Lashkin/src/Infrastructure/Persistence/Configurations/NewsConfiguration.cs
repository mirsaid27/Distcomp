using Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Persistence.Configurations;

public class NewsConfiguration : IEntityTypeConfiguration<News>
{
    public void Configure(EntityTypeBuilder<News> builder)
    {
        builder.HasKey(news => news.Id);

        builder.Property(news => news.Title).HasMaxLength(64).IsRequired();

        builder.HasIndex(news => news.Title).IsUnique();

        builder.Property(news => news.Content).HasMaxLength(2048).IsRequired();

        builder.Property(news => news.Created).IsRequired();

        builder.Property(news => news.Modified).IsRequired();

        builder.HasOne(news => news.User)
            .WithMany(user => user.News)
            .OnDelete(DeleteBehavior.Restrict)
            .IsRequired();

        builder.HasMany(news => news.Notices)
            .WithOne(notice => notice.News)
            .OnDelete(DeleteBehavior.Cascade)
            .IsRequired();

        builder.HasMany(news => news.Labels)
            .WithMany(labels => labels.News);
    }
}