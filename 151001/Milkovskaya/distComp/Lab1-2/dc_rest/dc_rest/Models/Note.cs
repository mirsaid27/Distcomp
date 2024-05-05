using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dc_rest.Models;

[Table("tbl_note")]
public class Note
{
    // Primary key
    public long Id { get; set; }
    [ForeignKey("NewsId")]
    // Foreign key
    public long NewsId { get; set; }
    public string Content { get; set; }
    
    // Navigation properties
    public News News { get; set; }
}