using AutoMapper;
using dc_rest.DTOs.RequestDTO;
using dc_rest.DTOs.ResponseDTO;
using dc_rest.Exceptions;
using dc_rest.Models;
using dc_rest.Repositories.Interfaces;
using dc_rest.Services.Interfaces;
using dc_rest.Utilities;
using FluentValidation;
using Newtonsoft.Json;

namespace dc_rest.Services;

public class NoteService : INoteService
{
    private IMapper _mapper;
    private INoteRepository _repository;
    private AbstractValidator<NoteRequestDto> _validation;
    private IHttpClientFactory _httpClientFactory;
    private IBaseService _baseService;
    public NoteService(IBaseService baseService)
    {
        _baseService = baseService;
    }
    public async Task<IEnumerable<NoteResponseDto>> GetNotesAsync()
    {
        var response =  await _baseService.SendAsync(new RequestDto()
        {
            ApiType = SD.ApiType.GET,
            Url = SD.NoteAPIBase + "/api/v1.0/notes"
        });
        return JsonConvert.DeserializeObject<IEnumerable<NoteResponseDto>>(Convert.ToString(response));
    }

    public async Task<NoteResponseDto> GetNoteByIdAsync(long id)
    {
        var response =  await _baseService.SendAsync(new RequestDto()
        {
            ApiType = SD.ApiType.GET,
            Url = SD.NoteAPIBase + "/api/v1.0/notes/"+id,
        });
        return JsonConvert.DeserializeObject<NoteResponseDto>(Convert.ToString(response));
    }

    public async Task<NoteResponseDto> CreateNoteAsync(NoteRequestDto note)
    {
        var response =  await _baseService.SendAsync(new RequestDto()
        {
            ApiType = SD.ApiType.POST,
            Url = SD.NoteAPIBase + "/api/v1.0/notes",
            Data = note,
        });
        return JsonConvert.DeserializeObject<NoteResponseDto>(Convert.ToString(response));
    }

    public async Task<NoteResponseDto> UpdateNoteAsync(NoteRequestDto note)
    {
        var response =  await _baseService.SendAsync(new RequestDto()
        {
            ApiType = SD.ApiType.PUT,
            Url = SD.NoteAPIBase + "/api/v1.0/notes",
            Data = note,
        });
        return JsonConvert.DeserializeObject<NoteResponseDto>(Convert.ToString(response));
    }

    public async Task DeleteNoteAsync(long id)
    {
        var response =  await _baseService.SendAsync(new RequestDto()
        {
            ApiType = SD.ApiType.DELETE,
            Url = SD.NoteAPIBase + "/api/v1.0/notes/"+id,
        });
        //return JsonConvert.DeserializeObject<NoteResponseDto>(Convert.ToString(response));
    }
}