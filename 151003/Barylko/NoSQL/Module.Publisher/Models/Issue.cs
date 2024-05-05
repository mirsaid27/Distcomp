using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;
namespace Publisher.Models;

[Table("tbl_issues")]
[Index(nameof(Title), IsUnique = true)]
public class Issue
{
    public long Id { get; set; }

    [ForeignKey("User")] public long? UserId { get; set; }

    [MaxLength(32)] public string Title { get; set; } = string.Empty;
    [MaxLength(2048)] public string Content { get; set; } = string.Empty;

    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public DateTime? Created { get; init; }

    [DatabaseGenerated(DatabaseGeneratedOption.Computed)]
    public DateTime? Modified { get; set; }

    public User? User { get; set; }
    public List<Tag> Tags { get; } = [];
}