using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dc_rest.Models;

[Table("tbl_newsLabel")]
public class NewsLabel
{
    // Primary key
    [Key]
    public long Id { get; set; }
    [ForeignKey("NewsId")]
    // Foreign key
    public long NewsId { get; set; }
    [ForeignKey("LabelId")]
    // Foreign key
    public long LabelId { get; set; }
}