using System.ComponentModel.DataAnnotations;

namespace Publisher.Models.DTOs.ResponceTo
{
    public class CommentRequestTo
    {
        public int? id { get; set; }

        public int? tweetId { get; set; } = null;

        public string? content { get; set; } = null;
    }
}
