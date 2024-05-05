using Cassandra.Data.Linq;
using Discussion.Dto.Request;
using Discussion.Dto.Response;
using Discussion.Exceptions;
using Discussion.Mappers;
using Discussion.Models;
using Discussion.Services.Interfaces;
using Discussion.Storage;
namespace Discussion.Services.Implementations;


public sealed class CommentService(CassandraDbContext context, ILogger<CommentService> logger) : ICommentService
{
    public async Task<CommentResponseTo> GetCommentById(long id)
    {
        Comment? comment = await context.Comments.FirstOrDefault(p => p.Id == id).ExecuteAsync();
        if (comment == null) throw new EntityNotFoundException($"Comment with id = {id} doesn't exist.");

        logger.LogInformation("Comment with id = {Id} was found", id);
        return comment.ToResponse();
    }

    public async Task<IEnumerable<CommentResponseTo>> GetComments()
    {
        logger.LogInformation("Getting all comments");
        return (await context.Comments.ExecuteAsync()).ToResponse();
    }

    public async Task<CommentResponseTo> CreateComment(CommentRequestTo commentRequestTo)
    {
        Comment comment = commentRequestTo.ToEntity();
        await context.Comments.Insert(comment).ExecuteAsync();
        logger.LogInformation("Comment with id = {Id} was created", comment.Id);
        return comment.ToResponse();
    }

    public async Task DeleteComment(long id)
    {
        Comment? comment = await context.Comments.FirstOrDefault(p => p.Id == id).ExecuteAsync();
        if (comment == null) throw new EntityNotFoundException($"Comment with id = {id} doesn't exist.");

        await context.Comments.Where(p => p.Country == comment.Country && p.IssueId == comment.IssueId && p.Id == comment.Id)
            .Delete()
            .ExecuteAsync();
        logger.LogInformation("Comment with id = {Id} was deleted", id);
    }

    public async Task<CommentResponseTo> UpdateComment(CommentRequestTo modifiedComment)
    {
        Comment? comment = await context.Comments.FirstOrDefault(p => p.Id == modifiedComment.Id).ExecuteAsync();
        if (comment == null) throw new EntityNotFoundException($"Comment with id = {modifiedComment.Id} doesn't exist.");

        await context.Comments.Where(p => p.Country == comment.Country && p.IssueId == comment.IssueId && p.Id == comment.Id)
            .Select(p => new Comment
            {
                Content = modifiedComment.Content
            })
            .Update()
            .ExecuteAsync();

        return modifiedComment.ToEntity().ToResponse();
    }
}
