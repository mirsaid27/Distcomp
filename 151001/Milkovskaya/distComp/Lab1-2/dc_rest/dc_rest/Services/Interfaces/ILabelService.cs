using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;

namespace dc_rest.Services.Interfaces;

public interface ILabelService
{
    Task<IEnumerable<LabelResponseDto>> GetLabelsAsync();

    Task<LabelResponseDto> GetLabelByIdAsync(long id);

    Task<LabelResponseDto> CreateLabelAsync(LabelRequestDto label);

    Task<LabelResponseDto> UpdateLabelAsync(LabelRequestDto label);

    Task DeleteLabelAsync(long id);
}