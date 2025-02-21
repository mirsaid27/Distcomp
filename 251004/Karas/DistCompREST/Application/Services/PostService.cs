using Application.Contracts.RepositoryContracts;
using Application.Contracts.ServiceContracts;
using Application.Dto.Request;
using Application.Dto.Response;
using Application.Validation;
using AutoMapper;
using Domain.Entities;
using FluentValidation;

namespace Application.Services;

public class PostService : IPostService
{
    private readonly IPostRepository _postRepository;
    private readonly IMapper _mapper;
    private readonly PostRequestDtoValidator _validator;
    
    public PostService(IPostRepository postRepository, 
        IMapper mapper, PostRequestDtoValidator validator)
    {
        _postRepository = postRepository;
        _mapper = mapper;
        _validator = validator;
    }
    
    public async Task<IEnumerable<PostResponseDto>> GetPostsAsync()
    {
        var posts = await _postRepository.GetAllAsync();
        return _mapper.Map<IEnumerable<PostResponseDto>>(posts);
    }

    public async Task<PostResponseDto> GetPostByIdAsync(long id)
    {
        var post = await _postRepository.GetByIdAsync(id)
                      ?? throw new NotFoundException(ErrorCodes.PostNotFound, ErrorMessages.PostNotFoundMessage(id));
        return _mapper.Map<PostResponseDto>(post);
    }

    public async Task<PostResponseDto> CreatePostAsync(PostRequestDto post)
    {
        await _validator.ValidateAndThrowAsync(post);
        var postToCreate = _mapper.Map<Post>(post);
        var createdPost = await _postRepository.CreateAsync(postToCreate);
        return _mapper.Map<PostResponseDto>(createdPost);
    }

    public async Task<PostResponseDto> UpdatePostAsync(PostRequestDto post)
    {
        await _validator.ValidateAndThrowAsync(post);
        var postToUpdate = _mapper.Map<Post>(post);
        var updatedPost = await _postRepository.UpdateAsync(postToUpdate)
                             ?? throw new NotFoundException(ErrorCodes.PostNotFound, ErrorMessages.PostNotFoundMessage(post.Id));
        return _mapper.Map<PostResponseDto>(updatedPost);
    }

    public async Task DeletePostAsync(long id)
    {
        if (!await _postRepository.DeleteAsync(id))
        {
            throw new NotFoundException(ErrorCodes.PostNotFound, ErrorMessages.PostNotFoundMessage(id));
        }
    }
}