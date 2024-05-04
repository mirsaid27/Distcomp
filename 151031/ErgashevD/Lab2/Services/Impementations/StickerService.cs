using AutoMapper;
using FluentValidation;
using Lab2.DTO.RequestDTO;
using Lab2.DTO.ResponseDTO;
using Lab2.Exceptions;
using Lab2.Infrastructure.Validators;
using Lab2.Models;
using Lab2.Repositories.Interfaces;
using Lab2.Services.Interfaces;

namespace Lab2.Services.Impementations
{
    public class StickerService(IStickerRepository stickerRepository,
        IMapper mapper, StickerRequestDtoValidator validator) : IStickerService
    {
        private readonly IStickerRepository _stickerRepository = stickerRepository;
        private readonly IMapper _mapper = mapper;
        private readonly StickerRequestDtoValidator _validator = validator;

        public async Task<IEnumerable<StickerResponseDto>> GetStickersAsync()
        {
            var stickers = await _stickerRepository.GetAllAsync();
            return _mapper.Map<IEnumerable<StickerResponseDto>>(stickers);
        }

        public async Task<StickerResponseDto> GetStickerByIdAsync(long id)
        {
            var sticker = await _stickerRepository.GetByIdAsync(id)
                ?? throw new NotFoundException(ErrorMessages.StickerNotFoundMessage(id));
            return _mapper.Map<StickerResponseDto>(sticker);
        }

        public async Task<StickerResponseDto> CreateStickerAsync(StickerRequestDto sticker)
        {
            _validator.ValidateAndThrow(sticker);
            var stickerToCreate = _mapper.Map<Sticker>(sticker);
            var createdSticker = await _stickerRepository.CreateAsync(stickerToCreate);
            return _mapper.Map<StickerResponseDto>(createdSticker);
        }

        public async Task<StickerResponseDto> UpdateStickerAsync(StickerRequestDto sticker)
        {
            _validator.ValidateAndThrow(sticker);
            var stickerToUpdate = _mapper.Map<Sticker>(sticker);
            var updatedSticker = await _stickerRepository.UpdateAsync(stickerToUpdate)
                ?? throw new NotFoundException(ErrorMessages.StickerNotFoundMessage(sticker.Id));
            return _mapper.Map<StickerResponseDto>(updatedSticker);
        }

        public async Task DeleteStickerAsync(long id)
        {
            if (!await _stickerRepository.DeleteAsync(id))
            {
                throw new NotFoundException(ErrorMessages.StickerNotFoundMessage(id));
            }
        }
    }
}
