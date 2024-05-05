using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Response;
using Publisher.Models;
using Riok.Mapperly.Abstractions;
namespace Publisher.Mappers;

[Mapper]
public static partial class IssueMapper
{
    public static partial Issue ToEntity(this CreateIssueRequestTo createIssueRequestTo);
    public static partial IssueResponseTo ToResponse(this Issue Issue);
    public static partial IEnumerable<IssueResponseTo> ToResponse(this IEnumerable<Issue> Issues);
}