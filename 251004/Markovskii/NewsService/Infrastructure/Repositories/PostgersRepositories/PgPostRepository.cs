using System.Runtime.InteropServices.JavaScript;
using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;
using Microsoft.Extensions.Options;
using Npgsql;

namespace Infrastructure.Repositories.PostgersRepositories;

public class PgPostRepository : PgRepository, IPostRepository
{
    public PgPostRepository(IOptions<InfrastructureOptions> settings) : base(settings.Value)
    {

    }
    public async Task<Post?> AddPost(Post post)
    {
        const string sqlCreatePost = 
                """
                   INSERT
                     INTO tbl_post
                          (
                            news_id
                          , content
                          )
                   VALUES (
                            @NewsId
                          , @Content
                          )
                RETURNING id
                """;
                
                await using var connection = await GetConnection();
                await using var cmd = new NpgsqlCommand(sqlCreatePost, connection);
                cmd.Parameters.AddWithValue("NewsId", post.NewsId);
                cmd.Parameters.AddWithValue("Content", post.Content);
        
                try{
                    using var reader = await cmd.ExecuteReaderAsync(System.Data.CommandBehavior.SingleResult);
                    
                    if (await reader.ReadAsync())
                    {
                        var postId = reader.GetInt64(0);
                        return new Post { 
                            Id = postId, 
                            NewsId = post.NewsId,
                            Content = post.Content
                        };
                    }
                }
                catch(NpgsqlException ex) when (ex.SqlState == "23505")
                {
                    throw new AlreadyExistsException();
                }
                catch (NpgsqlException ex) when (ex.SqlState == "23503")
                {
                    new NotFoundException("Id", $"{post.Id}");
                }
                throw new BadRequestException("Error", new Dictionary<string, string[]>());
    }

    public async Task<Post?> GetPost(long postId)
    {
        const string sqlGetReactionById =
            """
            SELECT id
                 , news_id
                 , content
              FROM tbl_post
             WHERE id = @Id
            """;

        await using var connection = await GetConnection();

        using var cmd = new NpgsqlCommand(sqlGetReactionById, connection);
        cmd.Parameters.AddWithValue("Id", postId);


        var reader = await cmd.ExecuteReaderAsync();

        if (await reader.ReadAsync())
        {
            var Id = reader.GetInt64(reader.GetOrdinal("id"));
            var postNewsId = reader.GetInt64(reader.GetOrdinal("news_id"));
            var postContent = reader.GetString(reader.GetOrdinal("content"));

            return new Post
            {
                Id = postId,
                NewsId = postNewsId,
                Content = postContent
            };
        }
        else
        {
            new NotFoundException("Id", $"{postId}");
        }

        throw new BadRequestException("Error", new Dictionary<string, string[]>());
    }

    public async Task<Post?> RemovePost(long postId)
    {
        const string sqlDeleteReaction = 
            """
            DELETE
              FROM tbl_post
             WHERE id = @Id
            """;

        await using var connection = await GetConnection();

        using var command = new NpgsqlCommand(sqlDeleteReaction, connection);
        command.Parameters.AddWithValue("Id", postId);

        int result;

        try{
            result = await command.ExecuteNonQueryAsync();
        }
        catch(NpgsqlException ex){
            throw new BadRequestException("Error", new Dictionary<string, string[]>());
        }

        return result == 0 ? throw new NotFoundException("Id", $"{postId}") : new Post();
    }

    public async Task<Post?> UpdatePost(Post post)
    {
        const string sqlUpdateReaction = 
            """
               UPDATE tbl_post
                  SET news_id = @NewsId
                    , content = @Content
                WHERE id = @Id
            RETURNING id
            """;

        await using var connection = await GetConnection();
        using var cmd = new NpgsqlCommand(sqlUpdateReaction, connection);

        cmd.Parameters.AddWithValue("Id", post.Id);
        cmd.Parameters.AddWithValue("NewsId", post.NewsId);
        cmd.Parameters.AddWithValue("Content", post.Content);

        try {
            var returnedId = await cmd.ExecuteScalarAsync();

            if (returnedId != null && (long)returnedId == post.Id)
            {
                return new Post {
                    Id = (long)returnedId,
                    NewsId = post.NewsId,
                    Content = post.Content
                };
            }
        }
        catch(NpgsqlException ex) when (ex.SqlState == "23505")
        {
            throw new AlreadyExistsException();
        }
        catch (NpgsqlException ex) when (ex.SqlState == "23503")
        {
            new NotFoundException("Id", $"{post.Id}");
        }
        throw new BadRequestException("Error", new Dictionary<string, string[]>());
    }

    public async Task<IEnumerable<Post?>?> GetAllPosts()
    {
        const string sqlGetReactions =
            """
            SELECT id
                 , news_id
                 , content
              FROM tbl_post
            """;

        await using var connection = await GetConnection();
        using var cmd = new NpgsqlCommand(sqlGetReactions, connection);



        using var reader = await cmd.ExecuteReaderAsync();

        var posts = new List<Post>();

        while (await reader.ReadAsync())
        {
            var post = new Post
            {
                Id = reader.GetInt64(reader.GetOrdinal("id")),
                NewsId = reader.GetInt64(reader.GetOrdinal("news_id")),
                Content = reader.GetString(reader.GetOrdinal("content"))
            };

            posts.Add(post);
        }

        return posts;

    }

    public async Task<IEnumerable<Post?>?> GetPostsByNewsId(long newsId)
    {
        throw new NotImplementedException();
    }
}