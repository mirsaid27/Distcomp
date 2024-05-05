using dc_rest.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace dc_rest.Data.Configurations;

public class CreatorConfiguration : IEntityTypeConfiguration<Creator>
{
    public void Configure(EntityTypeBuilder<Creator> builder)
    {
        builder.HasKey(b => b.Id);
        builder.Property(e => e.Id)
            .UseIdentityColumn()
            .IsRequired()
            .ValueGeneratedOnAdd();
        builder.HasIndex(b => b.Login).IsUnique();
    }
}