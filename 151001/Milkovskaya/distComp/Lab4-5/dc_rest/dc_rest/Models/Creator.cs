using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace dc_rest.Models;

[Table("tbl_creator")]
public class Creator
{
    // Primary key
    public long Id { get; set; }
    public string Firstname { get; set; }
    public string Lastname { get; set; }
    public string Login { get; set; }
    public string Password { get; set; }
    
    // Navigation properties
    public ICollection<News> NewsCollection { get; set; }
}