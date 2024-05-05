using Microsoft.EntityFrameworkCore;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Publisher.Exceptions;
using Publisher.Mappers;
using Publisher.Models;
using Publisher.Services.Interfaces;
using Publisher.Storage;
namespace Publisher.Services.Implementations;

public sealed class IssueService(AppDbContext context) : IIssueService
{
    public async Task<IssueResponseTo> GetIssueById(long id)
    {
        Issue? Issue = await context.Issues.FindAsync(id);
        if (Issue == null) throw new EntityNotFoundException($"Issue with id = {id} doesn't exist.");

        return Issue.ToResponse();
    }

    public async Task<IEnumerable<IssueResponseTo>> GetIssues()
    {
        return (await context.Issues.ToListAsync()).ToResponse();
    }

    public async Task<IssueResponseTo> CreateIssue(CreateIssueRequestTo createIssueRequestTo)
    {
        Issue Issue = createIssueRequestTo.ToEntity();
        await context.Issues.AddAsync(Issue);
        await context.SaveChangesAsync();
        return Issue.ToResponse();
    }

    public async Task DeleteIssue(long id)
    {
        Issue? Issue = await context.Issues.FindAsync(id);
        if (Issue == null) throw new EntityNotFoundException($"Issue with id = {id} doesn't exist.");

        context.Issues.Remove(Issue);
        await context.SaveChangesAsync();
    }

    public async Task<IssueResponseTo> UpdateIssue(UpdateIssueRequestTo modifiedIssue)
    {
        Issue? Issue = await context.Issues.FindAsync(modifiedIssue.Id);
        if (Issue == null) throw new EntityNotFoundException($"Issue with id = {modifiedIssue.Id} doesn't exist.");

        context.Entry(Issue).State = EntityState.Modified;

        Issue.Id = modifiedIssue.Id;
        Issue.Content = modifiedIssue.Content;
        Issue.UserId = modifiedIssue.UserId;
        Issue.Title = modifiedIssue.Title;

        await context.SaveChangesAsync();
        return Issue.ToResponse();
    }
}
