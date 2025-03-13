using Domain.Entities;

namespace Domain.Repositories;

public interface IPostRepository
{
    Task<PostMongoDb?> AddPost(PostMongoDb post);
    Task<PostMongoDb?> GetPost(long postId);
    Task<PostMongoDb?> RemovePost(long postId);
    Task<PostMongoDb?> UpdatePost(PostMongoDb post);
    Task<IEnumerable<PostMongoDb?>?> GetAllPosts();
    Task<IEnumerable<PostMongoDb?>?> GetPostsByNewsId(long newsId);
}