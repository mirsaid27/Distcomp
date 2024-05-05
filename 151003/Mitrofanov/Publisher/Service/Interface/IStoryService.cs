using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Service.Interface.Common;

namespace Publisher.Service.Interface
{
    public interface IStoryService : ICrudService<Story, StoryRequestTO, StoryResponseTO>
    {
        Task<StoryResponseTO> GetStoryByParam(IList<string> markerNames, IList<int> markerIds, string editorLogin,
            string title, string content);
    }
}
