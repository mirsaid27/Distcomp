using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class EditorRepository(DbStorage context) : AbstractCrudRepository<Editor>(context), IEditorRepository
    {
        public Story GetByStoryId(int storyId)
        {
            throw new NotImplementedException();
        }

        public Task<Story> GetByStoryIdAsync(int storyId)
        {
            throw new NotImplementedException();
        }
    }
}
