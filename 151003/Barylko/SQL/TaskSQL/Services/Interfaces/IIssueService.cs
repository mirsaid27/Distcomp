using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;

namespace TaskREST.Services.Interfaces;

public interface IIssueService
{
    Task<IssueResponseTo> GetIssueById(long id);
    Task<IEnumerable<IssueResponseTo>> GetIssues();
    Task<IssueResponseTo> CreateIssue(CreateIssueRequestTo createIssueRequestTo);
    Task DeleteIssue(long id);
    Task<IssueResponseTo> UpdateIssue(UpdateIssueRequestTo modifiedIssue);
}