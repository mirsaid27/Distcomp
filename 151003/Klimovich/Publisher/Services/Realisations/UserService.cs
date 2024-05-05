using AutoMapper;
using Publisher.Models;
using Publisher.Models.DTO.DTOs;
using Publisher.Models.DTO.ResponseTo;
using Publisher.Repositories;
using Publisher.Services.Interfaces;

namespace Publisher.Services.Realisation
{
    public class UserService : IUserService
    {
        private IRepository<User> _repository;
        private IMapper _mapper;

        public UserService(IRepository<User> repository, IMapper mapper)
        {
            _repository = repository;
            _mapper = mapper;
        }
        public UserResponceTo CreateUser(UserRequestTo item)
        {
            User n = _mapper.Map<User>(item);
            var res = _repository.Create(n);
            return _mapper.Map<UserResponceTo>(res);
        }

        public int DeleteUser(int id)
        {
            int res = _repository.Delete(id);
            return res;
        }

        public UserResponceTo GetUser(int id)
        {
            return _mapper.Map<UserResponceTo>(_repository.Get(id));
        }

        public List<UserResponceTo> GetUsers()
        {
            List<UserResponceTo> res = [];
            foreach (User n in _repository.GetAll())
            {
                res.Add(_mapper.Map<UserResponceTo>(n));
            }
            return res;
        }

        public UserResponceTo UpdateUser(UserRequestTo item)
        {
            var n = _mapper.Map<User>(item);
            var res = _repository.Update(n);
            return _mapper.Map<UserResponceTo>(res);
        }
    }
}
