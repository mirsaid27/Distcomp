using Lab2.Models;

namespace Lab2.DTO.ResponseDTO
{
    public class NoteResponseDto
    {
        public long Id { get; set; }
        public long NewsId { get; set; }
        public News News { get; set; }
        public string Content { get; set; }
    }
}
