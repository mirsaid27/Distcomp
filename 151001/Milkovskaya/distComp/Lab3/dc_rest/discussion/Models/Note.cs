using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace discussion.Models;

public class Note
{
    public long Id { get; set; }
    public string Country { get; set; }
    public long NewsId { get; set; }
    public string Content { get; set; }
    
    // Navigation properties
    //public News News { get; set; }
}