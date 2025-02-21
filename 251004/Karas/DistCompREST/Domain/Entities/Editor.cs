using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Domain.Entities;

public class Editor : BaseModel
{
    [Required]
    [MinLength(2)]
    [MaxLength(64)]
    [Column(TypeName = "text")]
    public string Login { get; set; }
    
    [Required]
    [MinLength(8)]
    [MaxLength(128)]
    [Column(TypeName = "text")]
    public string Password { get; set; }
    
    [Required]
    [MinLength(2)]
    [MaxLength(64)]
    [Column(TypeName = "text")]
    public string Firstname { get; set; }
    
    [Required]
    [MinLength(2)]
    [MaxLength(64)]
    [Column(TypeName = "text")]
    public string Lastname { get; set; }

    public virtual List<Article> Articles { get; set; } = [];
}