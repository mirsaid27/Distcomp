using dc_rest.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace dc_rest.Data.Configurations;

public class NoteConfiguration : IEntityTypeConfiguration<Note>
{
    public void Configure(EntityTypeBuilder<Note> builder)
    {
        builder.HasKey(b => b.Id);
        builder.Property(e => e.Id)
            .UseIdentityColumn()
            .IsRequired()
            .ValueGeneratedOnAdd();

        builder.HasOne(n => n.News)
                .WithMany(news => news.NoteCollection)
                .HasForeignKey(n => n.NewsId);
    }
}