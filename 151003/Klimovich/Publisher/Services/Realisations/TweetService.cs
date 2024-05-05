using AutoMapper;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Models;
using Publisher.Services.Interfaces;
using Publisher.Models.DTOs.DTO;
using Publisher.Repositories;
using Publisher.Models;

namespace Publisher.Services.Realisation
{
    public class TweetService : ITweetService
    {
        private IRepository<Tweet> _repository;
        private IMapper _mapper;

        public TweetService(IRepository<Tweet> repository, IMapper mapper)
        {
            _repository = repository;
            _mapper = mapper;
        }
        public TweetResponceTo CreateTweet(TweetRequestTo item)
        {
            item.created = DateTime.UtcNow;
            item.modified = DateTime.UtcNow;
            Tweet n = _mapper.Map<Tweet>(item);
            var res = _repository.Create(n);
            return _mapper.Map<TweetResponceTo>(res);
        }

        public int DeleteTweet(int id)
        {
            int res = _repository.Delete(id);
            return res;
        }

        public TweetResponceTo GetTweet(int id)
        {
            return _mapper.Map<TweetResponceTo>(_repository.Get(id));
        }

        public List<TweetResponceTo> GetTweets()
        {
            List<TweetResponceTo> res = new List<TweetResponceTo>();
            foreach (Tweet n in _repository.GetAll())
            {
                res.Add(_mapper.Map<TweetResponceTo>(n));
            }
            return res;
        }

        public TweetResponceTo UpdateTweet(TweetRequestTo item)
        {
            var n = _mapper.Map<Tweet>(item);
            var res = _repository.Update(n);
            return _mapper.Map<TweetResponceTo>(res);
        }
    }
}
