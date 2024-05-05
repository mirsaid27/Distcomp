using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Models
{
    [Table("tbl_comment")]
    public class Comment
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public int id { get; set; }

        [Required]
        [ForeignKey("Tweet")]
        public int tweetId { get; set; }
        public Tweet Tweet { get; set; }


        [MaxLength(2048)]
        public string content { get; set; }
    }
}
