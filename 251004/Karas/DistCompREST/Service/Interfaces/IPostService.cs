using Service.DTO.Request.Post;
using Service.DTO.Response.Post;

namespace Service.Interfaces;

public interface IPostService
{
    public Task<PostResponseToGetById?> CreatePost(PostRequestToCreate model);
    public Task<IEnumerable<PostResponseToGetById?>?> GetPosts(PostRequestToGetAll request);
    public Task<IEnumerable<PostResponseToGetById?>?> GetPostsByArticleId(PostRequestToGetByArticleId request);
    public Task<PostResponseToGetById?> GetPostById(PostRequestToGetById request);
    public Task<PostResponseToGetById?> UpdatePost(PostRequestToFullUpdate model);
    public Task<PostResponseToGetById?> DeletePost(PostRequestToDeleteById request);
}