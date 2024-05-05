using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Models
{
    [Table("tbl_sticker")]
    public class Sticker
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public int id { get; set; }

        [MaxLength(32)]
        [Required]
        public string name { get; set; }

        public List<Tweet> Tweets { get; set; } = new();
        public List<TweetSticker> TweetStickers { get; set; } = new();

    }
}
