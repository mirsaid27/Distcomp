using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Models
{
    [Table("tbl_user")]
    public class User
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public int id { get; set; }

        [Required]
        [MaxLength(64)]
        public string login { get; set; }

        [Required]
        [MaxLength(128)]
        public string password { get; set; }

        [Required]
        [MaxLength(64)]
        public string firstname { get; set; }

        [Required]
        [MaxLength(64)]
        public string lastname { get; set; }

        public List<Tweet>? Tweets { get; set; } = new();
    }
}
