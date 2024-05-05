using System.ComponentModel.DataAnnotations;

namespace Publisher.Models.DTOs.DTO
{
    public class StickerResponceTo
    {
        [Required]
        public int id { get; set; }
        [Required]
        public string name { get; set; }
    }
}
