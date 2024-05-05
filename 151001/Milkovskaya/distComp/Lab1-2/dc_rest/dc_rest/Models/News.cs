using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dc_rest.Models;

[Table("tbl_news")]
public class News
{
    // Primary key
    public long Id { get; set; }
    [ForeignKey("CreatorId")]
    // Foreign key
    public long CreatorId{ get; set; }
    public string Title { get; set; }
    public string Content { get; set; }
    public DateTime Created { get; set; }
    public DateTime Modified { get; set; }
    
    // Navigation properties
    public Creator Creator { get; set; }
    
    public ICollection<Label> LabelCollection { get; set; }
    public ICollection<Note> NoteCollection { get; set; }
}