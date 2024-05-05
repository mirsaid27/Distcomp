namespace discussion.Models.RequestDto;

public class KafkaRequestDto
{
    public HttpMethod Method { get; set; } = null!;
    
    public long? Id { get; set; }
    public NoteRequestDto? Request { get; set; }
}