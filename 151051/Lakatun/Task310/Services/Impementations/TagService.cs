using AutoMapper;
using FluentValidation;
using Task310.DTO.RequestDTO;
using Task310.DTO.ResponseDTO;
using Task310.Exceptions;
using Task310.Infrastructure.Validators;
using Task310.Models;
using Task310.Repositories.Interfaces;
using Task310.Services.Interfaces;

namespace Task310.Services.Impementations
{
    public class TagService(ITagRepository tagRepository,
        IMapper mapper, TagRequestDtoValidator validator) : ITagService
    {
        private readonly ITagRepository _tagRepository = tagRepository;
        private readonly IMapper _mapper = mapper;
        private readonly TagRequestDtoValidator _validator = validator;

        public async Task<IEnumerable<TagResponseDto>> GetTagsAsync()
        {
            var tags = await _tagRepository.GetAllAsync();
            return _mapper.Map<IEnumerable<TagResponseDto>>(tags);
        }

        public async Task<TagResponseDto> GetTagByIdAsync(long id)
        {
            var tag = await _tagRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.TagNotFoundMessage(id));
            return _mapper.Map<TagResponseDto>(tag);
        }

        public async Task<TagResponseDto> CreateTagAsync(TagRequestDto tag)
        {
            _validator.ValidateAndThrow(tag);
            var tagToCreate = _mapper.Map<Tag>(tag);
            var createdtag = await _tagRepository.CreateAsync(tagToCreate);
            return _mapper.Map<TagResponseDto>(createdTag);
        }

        public async Task<TagResponseDto> UpdateTagAsync(TagRequestDto tag)
        {
            _validator.ValidateAndThrow(tag);
            var tag = _mapper.Map<Tag>(tag);
            var updatedTag = await _tagRepository.UpdateAsync(tagToUpdate)
                ?? throw new NotFoundException(ErrorMessages.TagNotFoundMessage(tag.Id));
            return _mapper.Map<TagResponseDto>(updatedTag);
        }

        public async Task DeleteTagAsync(long id)
        {
            if (!await _tagRepository.DeleteAsync(id))
            {
                throw new NotFoundException(ErrorMessages.TagNotFoundMessage(id));
            }
        }
    }
}
