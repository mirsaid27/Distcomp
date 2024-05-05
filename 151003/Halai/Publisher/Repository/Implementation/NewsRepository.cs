using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class NewsRepository(DbStorage context) : AbstractCrudRepository<News>(context), INewsRepository
    {
        private readonly DbStorage _context = context;

        public override News Add(News news)
        {
            var author = new Author { Id = news.AuthorId };
            _context.Authors.Attach(author);
            news.Author = author;

            return base.Add(news);
        }

        public override async Task<News> AddAsync(News news)
        {
            var author = new Author { Id = news.AuthorId };
            _context.Authors.Attach(author);
            news.Author = author;

            return await base.AddAsync(news);
        }
    }
}
