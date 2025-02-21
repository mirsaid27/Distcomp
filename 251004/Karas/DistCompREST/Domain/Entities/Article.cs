using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Domain.Entities;

public class Article : BaseModel
{
    [Required]
    [MinLength(2)]
    [MaxLength(64)]
    [Column(TypeName = "text")]
    public string Title { get; set; }
    
    [Required]
    [MinLength(4)]
    [MaxLength(2048)]
    [Column(TypeName = "text")]
    public string Content { get; set; }
    
    [Required]
    [DataType(DataType.DateTime)]
    public DateTime Created { get; set; }
    
    [Required]
    [DataType(DataType.DateTime)]
    public DateTime Modified { get; set; }
    
    [Column("editor_id")]
    public long EditorId { get; set; }
    
    public virtual Editor Editor { get; set; }

    public virtual List<Post> Posts { get; set; } = [];

    public virtual List<Mark> Marks { get; set; } = [];
}