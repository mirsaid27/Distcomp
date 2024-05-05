using AutoMapper;
using Publisher.Models;
using Publisher.Models.DTOs.DTO;
using Publisher.Models.DTOs.ResponceTo;
using Publisher.Repositories;
using Publisher.Services.Interfaces;

namespace Publisher.Services.Realisation
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
            var res = _repository.Create(n);
            return _mapper.Map<CommentResponceTo>(res);
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
            var res = _repository.Update(n);
            return _mapper.Map<CommentResponceTo>(res);
        }
    }
}
