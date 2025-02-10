using AutoMapper;
using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;
using Service.DTO.Request.Post;
using Service.DTO.Response.Post;
using Service.Interfaces;

namespace Service.Services;

public class PostService (IPostRepository postRepository,IArticleRepository articleRepository, IMapper mapper) : IPostService
{
    public async Task<PostResponseToGetById?> CreatePost(PostRequestToCreate model)
    {
        var post = mapper.Map<Post>(model);
        Validate(post);
        post = await postRepository.AddPost(post);
        return mapper.Map<PostResponseToGetById>(post);
    }

    public async Task<IEnumerable<PostResponseToGetById?>?> GetPosts(PostRequestToGetAll request)
    {
        var post = await postRepository.GetAllPosts();
        return post.Select(mapper.Map<PostResponseToGetById>);
    }

    public async Task<IEnumerable<PostResponseToGetById?>?> GetPostsByArticleId(PostRequestToGetByArticleId request)
    {
        var posts = await postRepository.GetPostsByArticleId(request.ArticleId);
        return posts.Select(mapper.Map<PostResponseToGetById>);
    }

    public async Task<PostResponseToGetById?> GetPostById(PostRequestToGetById request)
    {
        var post = await postRepository.GetPost(request.Id);
        return mapper.Map<PostResponseToGetById>(post);
    }

    public async Task<PostResponseToGetById?> UpdatePost(PostRequestToFullUpdate model)
    {
        var post = mapper.Map<Post>(model);
        Validate(post);
        post = await postRepository.UpdatePost(post);
        return mapper.Map<PostResponseToGetById>(post);
    }

    public async Task<PostResponseToGetById?> DeletePost(PostRequestToDeleteById request)
    {
        var post = await postRepository.RemovePost(request.Id);
        return mapper.Map<PostResponseToGetById>(post);
    }
    
    private bool Validate(Post post)
    {
        var errors = new Dictionary<string, string[]>();
        if (post.Content.Length < 2 || post.Content.Length > 2048)
        {
            errors.Add("Content",["Should be from 2 to 64 chars"]);
        }
        if (errors.Count != 0)
        {
            throw new BadRequestException("Validation error", errors);
        }
        return true;
    }
}