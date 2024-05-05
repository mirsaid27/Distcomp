namespace dc_rest.DTOs.RequestDTO;

public class NoteRequestDto
{
    public long? Id { get; set; }
    public long NewsId { get; set; }
    public string Content { get; set; }
    public string? Country { get; set; }
}