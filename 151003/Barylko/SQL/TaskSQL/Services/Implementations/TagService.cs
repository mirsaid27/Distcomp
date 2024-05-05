using Microsoft.EntityFrameworkCore;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Exceptions;
using TaskREST.Services.Interfaces;
using TaskREST.Storage;
using TagMapper = TaskREST.Mappers.TagMapper;

namespace TaskREST.Services.Implementations;

public sealed class TagService(AppDbContext context) : ITagService
{
    public async Task<TagResponseTo> GetTagById(long id)
    {
        var tag = await context.tbl_tag.FindAsync(id);
        if (tag == null) throw new EntityNotFoundException($"Tag with id = {id} doesn't exist.");

        return TagMapper.Map(tag);
    }

    public async Task<IEnumerable<TagResponseTo>> GetTags()
    {
        return TagMapper.Map(await context.tbl_tag.ToListAsync());
    }

    public async Task<TagResponseTo> CreateTag(CreateTagRequestTo createTagRequestTo)
    {
        var tag = TagMapper.Map(createTagRequestTo);
        await context.tbl_tag.AddAsync(tag);
        await context.SaveChangesAsync();
        return TagMapper.Map(tag);
    }

    public async Task DeleteTag(long id)
    {
        var tag = await context.tbl_tag.FindAsync(id);
        if (tag == null) throw new EntityNotFoundException($"Tag with id = {id} doesn't exist.");

        context.tbl_tag.Remove(tag);
        await context.SaveChangesAsync();
    }

    public async Task<TagResponseTo> UpdateTag(UpdateTagRequestTo modifiedTag)
    {
        var tag = await context.tbl_tag.FindAsync(modifiedTag.id);
        if (tag == null) throw new EntityNotFoundException($"Tag with id = {modifiedTag.id} doesn't exist.");

        context.Entry(tag).State = EntityState.Modified;

        tag.id = modifiedTag.id;
        tag.Name = modifiedTag.Name;

        await context.SaveChangesAsync();
        return TagMapper.Map(tag);
    }
}