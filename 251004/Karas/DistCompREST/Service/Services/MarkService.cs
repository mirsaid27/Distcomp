using Service.DTO.Request;
using Service.DTO.Response;
using AutoMapper;
using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;
using Service.DTO.Request.Mark;
using Service.DTO.Response.Mark;
using Service.Interfaces;

namespace Service.Services;

public class MarkService (IMarkRepository markRepository, IMapper mapper) : IMarkService
{
    public async Task<MarkResponseToGetById?> CreateMark(MarkRequestToCreate model)
    {
        var mark = mapper.Map<Mark>(model);
        Validate(mark);
        mark = await markRepository.AddMark(mark);
        return mapper.Map<MarkResponseToGetById>(mark);
    }

    public async Task<IEnumerable<MarkResponseToGetById?>?> GetMarks(MarkRequestToGetAll request)
    {
        var mark = await markRepository.GetAllMarks();
        return mark.Select(mapper.Map<MarkResponseToGetById>);
    }

    public async Task<MarkResponseToGetById?> GetMarkById(MarkRequestToGetById request)
    {
        var mark = await markRepository.GetMark(request.Id);
        return mapper.Map<MarkResponseToGetById>(mark);
    }

    public async Task<MarkResponseToGetById?> UpdateMark(MarkRequestToFullUpdate model)
    {
        var mark = mapper.Map<Mark>(model);
        Validate(mark);
        mark = await markRepository.UpdateMark(mark);
        return mapper.Map<MarkResponseToGetById>(mark);
    }

    public async Task<MarkResponseToGetById?> DeleteMark(MarkRequestToDeleteById request)
    {
        var mark = await markRepository.RemoveMark(request.Id);
        return mapper.Map<MarkResponseToGetById>(mark);
    }
    
    private bool Validate(Mark mark)
    {
        var errors = new Dictionary<string, string[]>();
        if (mark.Name.Length < 2 || mark.Name.Length > 32)
        {
            errors.Add("Name",["Should be from 2 to 32 chars"]);
        }
        if (errors.Count != 0)
        {
            throw new BadRequestException("Validation error", errors);
        }
        return true;
    }
}