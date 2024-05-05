using Publisher.Entity.Db;
using Publisher.Repository.Implementation.Common;
using Publisher.Repository.Interface;
using Publisher.Storage.Common;

namespace Publisher.Repository.Implementation
{
    public class StoryRepository(DbStorage context) : AbstractCrudRepository<Story>(context), IStoryRepository
    {
        private readonly DbStorage _context = context;

        public override Story Add(Story story)
        {
            var editor = new Editor { Id = story.EditorId };
            _context.Editors.Attach(editor);
            story.Editor = editor;

            return base.Add(story);
        }

        public override async Task<Story> AddAsync(Story story)
        {
            var editor = new Editor { Id = story.EditorId };
            _context.Editors.Attach(editor);
            story.Editor = editor;

            return await base.AddAsync(story);
        }
    }
}
