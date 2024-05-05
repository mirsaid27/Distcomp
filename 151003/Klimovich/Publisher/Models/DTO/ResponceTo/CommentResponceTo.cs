using System.ComponentModel.DataAnnotations;

namespace Publisher.Models.DTOs.DTO
{
    public class CommentResponceTo
    {
        [Required]
        public int id { get; set; }
        [Required]
        public int tweetId { get; set; }
        [Required]
        public string content { get; set; }
    }
}
