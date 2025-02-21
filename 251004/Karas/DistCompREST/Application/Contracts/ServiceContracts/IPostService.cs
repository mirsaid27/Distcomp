using Application.Dto.Request;
using Application.Dto.Response;

namespace Application.Contracts.ServiceContracts;

public interface IPostService
{
    Task<IEnumerable<PostResponseDto>> GetPostsAsync();

    Task<PostResponseDto> GetPostByIdAsync(long id);

    Task<PostResponseDto> CreatePostAsync(PostRequestDto post);

    Task<PostResponseDto> UpdatePostAsync(PostRequestDto post);

    Task DeletePostAsync(long id);
}