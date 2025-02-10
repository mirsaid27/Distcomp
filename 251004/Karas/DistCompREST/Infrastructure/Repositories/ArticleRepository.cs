using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;

namespace Infrastructure.Repositories;

public class 
    ArticleRepository : IArticleRepository
{
    private readonly Dictionary<long, Article> _article = new ();
    private long _nextId = 1;

    public async Task<Article?> AddArticle(Article article)
    {
        article.Id = _nextId;
        _article.Add(_nextId,article);
        return _article[_nextId++];
    }

    public async Task<Article?> GetArticle(long articleId)
    {
        if (_article.TryGetValue(articleId, out Article article))
        {
            return article;
        }
        throw new NotFoundException("Id",$"{articleId}");
    }

    public async Task<Article?> RemoveArticle(long articleId)
    {
        if (_article.Remove(articleId, out Article article))
        {
            return article;
        }
        throw new NotFoundException("Id",$"{articleId}");
    }

    public async Task<Article?> UpdateArticle(Article article)
    {
        if (_article.ContainsKey(article.Id))
        {
            article.Modified = DateTime.Now;
            _article[article.Id] = article;
            return _article[article.Id];
        }
        throw new NotFoundException("Id",$"{article.Id}");
    }

    public async Task<IEnumerable<Article?>?> GetAllArticle()
    {
        return _article.Values.ToList();
    }
}