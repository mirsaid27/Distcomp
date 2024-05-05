

using dc_rest.DTOs.RequestDTO;

public class KafkaRequestDto
{
    public HttpMethod Method { get; set; } = null!;
    
    public long? Id { get; set; }
    public NoteRequestDto? Request { get; set; }
}