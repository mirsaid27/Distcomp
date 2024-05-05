using AutoMapper;
using Discussion.Models;
using Discussion.Models.DTO.RequestTo;
using Discussion.Models.DTO.ResponceTo;
using Discussion.Repositories;
using Discussion.Services.Interfaces;

namespace Discussion.Services.Realisation
{
    public class CommentService : ICommentService
    {
        private IRepository<Comment> _repository;
        private IMapper _mapper;

        public CommentService(IRepository<Comment> repository, IMapper mapper)
        {
            _repository = repository;
            _mapper = mapper;
        }
        public CommentResponceTo CreateComment(CommentRequestTo item)
        {
            Comment n = _mapper.Map<Comment>(item);
            n.country = "Belarus";
            _repository.Create(n);
            return _mapper.Map<CommentResponceTo>(n);
        }

        public int DeleteComment(int id)
        {
            int res = _repository.Delete(id);
            return res;
        }

        public CommentResponceTo GetComment(int id)
        {
            return _mapper.Map<CommentResponceTo>(_repository.Get(id));
        }

        public List<CommentResponceTo> GetComments()
        {
            List<CommentResponceTo> res = new List<CommentResponceTo>();
            foreach (Comment n in _repository.GetAll())
            {
                res.Add(_mapper.Map<CommentResponceTo>(n));
            }
            return res;
        }

        public CommentResponceTo UpdateComment(CommentRequestTo item)
        {
            var n = _mapper.Map<Comment>(item);
            _repository.Update(n);
            return _mapper.Map<CommentResponceTo>(n);
        }
    }
}
