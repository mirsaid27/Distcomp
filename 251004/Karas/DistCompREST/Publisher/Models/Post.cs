using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Publisher.Models;

public class Post : BaseModel
{
    [Required]
    [MinLength(2)]
    [MaxLength(2048)]
    [Column(TypeName = "text")]
    public string Content { get; set; }
    
    public long ArticleId { get; set; }
    public virtual Article Article { get; set; }
}