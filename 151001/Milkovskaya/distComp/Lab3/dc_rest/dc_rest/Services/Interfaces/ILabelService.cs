using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Services.Interfaces;

public interface ILabelService
{
    Task<IEnumerable<LabelResponseDto>> GetLabelsAsync();

    Task<LabelResponseDto> GetLabelByIdAsync(long id);

    Task<LabelResponseDto> CreateLabelAsync(LabelRequestDto sticker);

    Task<LabelResponseDto> UpdateLabelAsync(LabelRequestDto sticker);

    Task DeleteLabelAsync(long id);
}