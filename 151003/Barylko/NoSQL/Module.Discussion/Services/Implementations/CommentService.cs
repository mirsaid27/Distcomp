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
        Comment? Comment = await context.Comments.FirstOrDefault(p => p.Id == id).ExecuteAsync();
        if (Comment == null) throw new EntityNotFoundException($"Comment with id = {id} doesn't exist.");

        logger.LogInformation("Comment with id = {Id} was found", id);
        return Comment.ToResponse();
    }

    public async Task<IEnumerable<CommentResponseTo>> GetComments()
    {
        logger.LogInformation("Getting all Comments");
        return (await context.Comments.ExecuteAsync()).ToResponse();
    }

    public async Task<CommentResponseTo> CreateComment(CommentRequestTo CommentRequestTo)
    {
        Comment Comment = CommentRequestTo.ToEntity();
        await context.Comments.Insert(Comment).ExecuteAsync();
        logger.LogInformation("Comment with id = {Id} was created", Comment.Id);
        return Comment.ToResponse();
    }

    public async Task DeleteComment(long id)
    {
        Comment? Comment = await context.Comments.FirstOrDefault(p => p.Id == id).ExecuteAsync();
        if (Comment == null) throw new EntityNotFoundException($"Comment with id = {id} doesn't exist.");
        
        await context.Comments.Where(p => p.Country == Comment.Country && p.Id == Comment.Id).Delete().ExecuteAsync();
        logger.LogInformation("Comment with id = {Id} was deleted", id);
    }

    public async Task<CommentResponseTo> UpdateComment(CommentRequestTo modifiedComment)
    {
        Comment? Comment = await context.Comments.FirstOrDefault(p => p.Id == modifiedComment.Id).ExecuteAsync();
        if (Comment == null) throw new EntityNotFoundException($"Comment with id = {modifiedComment.Id} doesn't exist.");
        
        await context.Comments.Where(p => p.Country == Comment.Country && p.IssueId == Comment.IssueId && p.Id == Comment.Id)
            .Select(p => new Comment
            {
                Content = modifiedComment.Content
            })
            .Update()
            .ExecuteAsync();
        
        return modifiedComment.ToEntity().ToResponse();
    }
}
