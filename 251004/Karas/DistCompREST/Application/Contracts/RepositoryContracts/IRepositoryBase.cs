using System.Linq.Expressions;

namespace Application.Contracts.RepositoryContracts;

public interface IRepositoryBase<T> 
    where T : class 
{
    Task<IEnumerable<T>> GetAllAsync();

    Task<T?> GetByIdAsync(long id);

    Task<T> CreateAsync(T entity);

    Task<T?> UpdateAsync(T entity);

    Task<bool> DeleteAsync(long id);
}