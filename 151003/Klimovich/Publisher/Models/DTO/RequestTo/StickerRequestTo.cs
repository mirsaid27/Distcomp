using System.ComponentModel.DataAnnotations;

namespace DC.Models.DTOs.ResponceTo
{
    public class StickerRequestTo
    {
        public int? id { get; set; }

        public string? name { get; set; } = null;
    }
}
