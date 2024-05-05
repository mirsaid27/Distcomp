using Microsoft.EntityFrameworkCore;
using Publisher.Models;

namespace Publisher.Repositories
{
    public class CommentRepository : IRepository<Comment>
    {
        private ApplicationContext _dbContext;

        public CommentRepository(ApplicationContext dbContext)
        {
            _dbContext = dbContext;
        }
        public Comment Create(Comment item)
        {
            _dbContext.Add(item);
            _dbContext.SaveChanges();
            return item;
        }

        public int Delete(int id)
        {
            int res = _dbContext.Comments.Where(n => n.id == id).ExecuteDelete();
            _dbContext.SaveChanges();
            return res;
        }

        public Comment Get(int id)
        {
            var res = _dbContext.Comments.Where(n => n.id == id).ToList();
            Comment n;
            if (res.Count > 0)
            {
                n = res[0];
                return n;
            }
            else return null;
        }

        public List<Comment> GetAll()
        {
            return _dbContext.Comments.ToList();
        }

        public Comment Update(Comment item)
        {
            var res = _dbContext.Comments.Where(n => n.id == item.id).ToList();
            Comment n;
            if (res.Count > 0)
            {
                n = res[0];
            }
            else return null;
            if (item.tweetId != null)
            {
                n.tweetId = (int)item.tweetId;
            }
            if (item.content != null)
            {
                n.content = item.content;
            }
            _dbContext.Update(n);
            _dbContext.SaveChanges();
            return n;
        }
    }
}
