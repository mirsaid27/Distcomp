using System.Linq.Expressions;

namespace Domain.Interfaces;

public interface IRepositoryBase<T>
{
    IQueryable<T> FindAll(bool trackChanges);
    IQueryable<T> FindByCondition(Expression<Func<T, bool>> expression, bool trackChanges);
    Task<T?> FindByIdAsync(long id, CancellationToken cancellationToken = default);
    Task CreateAsync(T entity);
    void Update(T entity);
    void Delete(T entity);
}