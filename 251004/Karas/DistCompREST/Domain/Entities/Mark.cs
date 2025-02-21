using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Domain.Entities;

public class Mark : BaseModel
{
    [Required]
    [MinLength(2)]
    [MaxLength(32)]
    [Column(TypeName = "text")]
    public string Name { get; set; }

    public virtual List<Article> Articles { get; set; } = [];
}