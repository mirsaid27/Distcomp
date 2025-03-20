using Publisher.Data;
using Publisher.Models;
using Publisher.Repositories.Interfaces;

namespace Publisher.Repositories.Implementations;

public class EditorRepository : BaseRepository<Editor>, IEditorRepository
{
    public EditorRepository(AppDbContext context) : base(context)
    {
        
    }
}