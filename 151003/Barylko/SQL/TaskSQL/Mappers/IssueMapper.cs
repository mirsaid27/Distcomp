using Riok.Mapperly.Abstractions;
using TaskREST.Dto.Request.CreateTo;
using TaskREST.Dto.Request.UpdateTo;
using TaskREST.Dto.Response;
using TaskREST.Models;

namespace TaskREST.Mappers;

[Mapper]
public static partial class IssueMapper
{
    public static partial Issue Map(UpdateIssueRequestTo updateIssueRequestTo);
    public static partial Issue Map(CreateIssueRequestTo createIssueRequestTo);
    public static partial IssueResponseTo Map(Issue Issue);
    public static partial IEnumerable<IssueResponseTo> Map(IEnumerable<Issue> Issues);

    public static partial IEnumerable<Issue> Map(
        IEnumerable<UpdateIssueRequestTo> IssueRequestTos);
}