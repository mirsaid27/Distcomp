using System.ComponentModel.DataAnnotations;

namespace Publisher.Models.DTO.DTOs
{
    public class UserResponceTo
    {
        [Required]
        public int id { get; set; }
        [Required]
        public string login { get; set; }
        [Required]
        public string password { get; set; }
        [Required]
        public string firstname { get; set; }
        [Required]
        public string lastname { get; set; }
    }
}
