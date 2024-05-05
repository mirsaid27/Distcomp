using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dc_rest.Models;

[Table("tbl_label")]
public class Label
{
    // Primary key
    public long Id { get; set; }
    public string Name { get; set; }
    
    // Navigation properties
    public ICollection<News> NewsCollection { get; set; }
}