using Publisher.Entity.Db;
using Publisher.Repository.Interface.Common;

namespace Publisher.Repository.Interface
{
    public interface IEditorRepository : ICrudRepository<Editor>
    {
        Story GetByStoryId(int storyId);
        Task<Story> GetByStoryIdAsync(int storyId);
    }
}
