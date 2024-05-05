
using dc_rest.DTOs.ResponseDTO;

public class KafkaResponseDto
{
    public NoteResponseDto? Response { get; set; }
    public List<NoteResponseDto>? ResponseList { get; set; }
    public ErrorResponseDto? Error { get; set; }
}