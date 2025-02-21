using Application.Contracts.RepositoryContracts;
using Domain.Entities;

namespace Infrastructure.Repositories.Implementations;

public class EditorRepository : RepositoryBase<Editor>, IEditorRepository
{
    public EditorRepository(RepositoryContext context) : base(context)
    {
    }   
}