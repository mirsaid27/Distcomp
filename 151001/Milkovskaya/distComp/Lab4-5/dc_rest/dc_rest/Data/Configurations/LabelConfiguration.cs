using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Label = dc_rest.Models.Label;

namespace dc_rest.Data.Configurations;

public class LabelConfiguration : IEntityTypeConfiguration<Label>
{
    public void Configure(EntityTypeBuilder<Label> builder)
    {
        builder.HasKey(b => b.Id);
        builder.HasIndex(b => b.Name).IsUnique();
        builder.Property(e => e.Id)
            .UseIdentityColumn()
            .IsRequired()
            .ValueGeneratedOnAdd();
    }
}