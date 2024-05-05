using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
namespace Publisher.Services.Interfaces;

public interface IIssueService
{
    Task<IssueResponseTo> GetIssueById(long id);
    Task<IEnumerable<IssueResponseTo>> GetIssues();
    Task<IssueResponseTo> CreateIssue(CreateIssueRequestTo createIssueRequestTo);
    Task DeleteIssue(long id);
    Task<IssueResponseTo> UpdateIssue(UpdateIssueRequestTo modifiedIssue);
}