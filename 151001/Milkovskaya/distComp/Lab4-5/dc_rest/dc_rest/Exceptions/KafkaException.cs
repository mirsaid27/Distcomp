using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Exceptions;

public class KafkaException(int code, ErrorResponseDto error): Exception(error.errorMessage)
{
    public int Code { get; } = code;
    public ErrorResponseDto ErrorResponse { get; set; } = error;
}