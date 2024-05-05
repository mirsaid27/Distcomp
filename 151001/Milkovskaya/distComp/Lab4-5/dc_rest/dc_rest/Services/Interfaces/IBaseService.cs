using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Services.Interfaces;

public interface IBaseService
{
    Task<string> SendAsync(RequestDto requestDto);
}