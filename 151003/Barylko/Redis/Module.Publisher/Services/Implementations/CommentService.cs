using Microsoft.EntityFrameworkCore;
using Publisher.Clients.Discussion;
using Publisher.Clients.Discussion.Dto.Response;
using Publisher.Clients.Discussion.Mapper;
using Publisher.Dto.Request.CreateTo;
using Publisher.Dto.Request.UpdateTo;
using Publisher.Dto.Response;
using Publisher.Exceptions;
using Publisher.Services.Interfaces;
using Publisher.Storage;
namespace Publisher.Services.Implementations;

public sealed class CommentService(IDiscussionService discussionService, AppDbContext dbContext) : ICommentService
{
    private readonly Random _random = new();

    public async Task<CommentResponseTo> GetCommentById(long id) =>
        (await discussionService.GetComment(id)).ToResponse();

    public async Task<IEnumerable<CommentResponseTo>> GetComments() =>
        (await discussionService.GetComments()).ToResponse();

    public async Task<CommentResponseTo> CreateComment(CreateCommentRequestTo createCommentRequestTo, string country)
    {
        var isIssueExist = await dbContext.Issues.AnyAsync(t => t.Id == createCommentRequestTo.IssueId);
        if (!isIssueExist)
            throw new EntityNotFoundException("Issue not found");

        DiscussionCommentResponseTo discussionCommentResponseTo = (await discussionService.CreateComment(createCommentRequestTo.ToDiscussionRequest(CreateId(), country)));
        return discussionCommentResponseTo.ToResponse();
    }

    public async Task DeleteComment(long id)
    {
        await discussionService.DeleteComment(id);
    }

    public async Task<CommentResponseTo> UpdateComment(UpdateCommentRequestTo modifiedComment, string country)
    {
        var isIssueExist = await dbContext.Issues.AnyAsync(t => t.Id == modifiedComment.IssueId);
        if (!isIssueExist)
            throw new EntityNotFoundException("Issue not found");

        return (await discussionService.UpdateComment(modifiedComment.ToDiscussionRequest(country))).ToResponse();
    }

    private long CreateId() => (DateTimeOffset.UtcNow.ToUnixTimeMilliseconds() << 24 | (uint)_random.Next(0, 1 << 24)) & long.MaxValue;
}
