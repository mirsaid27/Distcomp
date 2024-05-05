using AutoMapper;
using DC.Models.DTOs.ResponceTo;
using Publisher.Models;
using Publisher.Models.DTOs.DTO;
using Publisher.Repositories;
using Publisher.Services.Interfaces;

namespace Publisher.Services.Realisation
{
    public class StickerService : IStickerService
    {
        private IRepository<Sticker> _repository;
        private IMapper _mapper;

        public StickerService(IRepository<Sticker> repository, IMapper mapper)
        {
            _repository = repository;
            _mapper = mapper;
        }
        public StickerResponceTo CreateSticker(StickerRequestTo item)
        {
            Sticker n = _mapper.Map<Sticker>(item);
            var res = _repository.Create(n);
            return _mapper.Map<StickerResponceTo>(res);
        }

        public int DeleteSticker(int id)
        {
            int res = _repository.Delete(id);
            return res;
        }

        public StickerResponceTo GetSticker(int id)
        {
            return _mapper.Map<StickerResponceTo>(_repository.Get(id));
        }

        public List<StickerResponceTo> GetStickers()
        {
            List<StickerResponceTo> res = new List<StickerResponceTo>();
            foreach (Sticker n in _repository.GetAll())
            {
                res.Add(_mapper.Map<StickerResponceTo>(n));
            }
            return res;
        }

        public StickerResponceTo UpdateSticker(StickerRequestTo item)
        {
            var n = _mapper.Map<Sticker>(item);
            var res = _repository.Update(n);
            return _mapper.Map<StickerResponceTo>(res);
        }
    }
}
