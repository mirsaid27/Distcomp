using System.ComponentModel.DataAnnotations;

namespace Publisher.Models.DTOs.ResponceTo
{
    public class TweetRequestTo
    {
        public int? id { get; set; }


        public string? title { get; set; } = null;


        public string? content { get; set; } = null;


        public int? userId { get; set; } = null;

        public DateTime? created { get; set; } = null;
        public DateTime? modified { get; set; } = null;

    }
}
