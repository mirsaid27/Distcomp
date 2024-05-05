using dc_rest.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace dc_rest.Data.Configurations;

public class NewsConfiguration : IEntityTypeConfiguration<News>
{
    public void Configure(EntityTypeBuilder<News> builder)
    {
        builder.HasKey(b => b.Id);
        builder.Property(e => e.Id)
            .UseIdentityColumn()
            .IsRequired()
            .ValueGeneratedOnAdd();
        builder.HasIndex(b => b.Title).IsUnique();

        builder.HasOne(n => n.Creator)
            .WithMany(c => c.NewsCollection)
            .HasForeignKey(n => n.CreatorId);

        builder.HasMany(n => n.LabelCollection)
            .WithMany(l => l.NewsCollection)
            .UsingEntity<NewsLabel>(
                //"NewsLabel",
                j => j
                    .HasOne<Label>()
                    .WithMany()
                    .HasForeignKey("LabelId"),
                    //.OnDelete(DeleteBehavior.Cascade),
                j => j
                    .HasOne<News>()
                    .WithMany()
                    .HasForeignKey("NewsId"),
                    //.OnDelete(DeleteBehavior.Cascade),
                j =>
                {
                    j.ToTable("tbl_newsLabel");
                });


    }
}