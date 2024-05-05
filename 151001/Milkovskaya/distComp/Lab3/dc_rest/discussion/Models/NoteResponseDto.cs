namespace discussion.Models;

public class NoteResponseDto
{
    public long Id { get; set; }
    public long NewsId { get; set; }
    //public News News { get; set; }
    public string Content { get; set; }
    public string Country { get; set; }
}