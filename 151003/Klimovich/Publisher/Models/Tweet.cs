using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace Publisher.Models
{
    [Table("tbl_tweet")]
    public class Tweet
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Required]
        public int id { get; set; }


        [MaxLength(64)]
        public string title { get; set; }

        [MaxLength(2048)]
        public string content { get; set; }


        public DateTime created { get; set; }


        public DateTime modified { get; set; }

        [Required]
        [ForeignKey("User")]
        public int userId { get; set; }
        public User User { get; set; }
        public List<Comment>? Comments { get; set; } = new();
        public List<Sticker> Stickers { get; set; } = new();
        public List<TweetSticker> TweetStickers { get; set; } = new();

    }
}
