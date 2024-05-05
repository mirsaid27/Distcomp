using Microsoft.EntityFrameworkCore;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Exceptions;
using TaskREST.Services.Interfaces;
using TaskREST.Storage;
using IssueMapper = TaskREST.Mappers.IssueMapper;

namespace TaskREST.Services.Implementations;

public sealed class IssueService(AppDbContext context) : IIssueService
{
    public async Task<IssueResponseTo> GetIssueById(long id)
    {
        var Issue = await context.tbl_issue.FindAsync(id);
        if (Issue == null) throw new EntityNotFoundException($"Issue with id = {id} doesn't exist.");

        return IssueMapper.Map(Issue);
    }

    public async Task<IEnumerable<IssueResponseTo>> GetIssues()
    {
        return IssueMapper.Map(await context.tbl_issue.ToListAsync());
    }

    public async Task<IssueResponseTo> CreateIssue(CreateIssueRequestTo createIssueRequestTo)
    {
        var Issue = IssueMapper.Map(createIssueRequestTo);
        await context.tbl_issue.AddAsync(Issue);
        await context.SaveChangesAsync();
        return IssueMapper.Map(Issue);
    }

    public async Task DeleteIssue(long id)
    {
        var Issue = await context.tbl_issue.FindAsync(id);
        if (Issue == null) throw new EntityNotFoundException($"Issue with id = {id} doesn't exist.");

        context.tbl_issue.Remove(Issue);
        await context.SaveChangesAsync();
    }

    public async Task<IssueResponseTo> UpdateIssue(UpdateIssueRequestTo modifiedIssue)
    {
        var Issue = await context.tbl_issue.FindAsync(modifiedIssue.id);
        if (Issue == null) throw new EntityNotFoundException($"Issue with id = {modifiedIssue.id} doesn't exist.");

        context.Entry(Issue).State = EntityState.Modified;

        Issue.id = modifiedIssue.id;
        Issue.Content = modifiedIssue.Content;
        Issue.user_id = modifiedIssue.user_id;
        Issue.Title = modifiedIssue.Title;


        await context.SaveChangesAsync();
        return IssueMapper.Map(Issue);
    }
}