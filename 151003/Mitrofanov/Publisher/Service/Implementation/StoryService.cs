using AutoMapper;
using Microsoft.Extensions.Caching.Distributed;
using Newtonsoft.Json;
using Publisher.Entity.Db;
using Publisher.Entity.DTO.RequestTO;
using Publisher.Entity.DTO.ResponseTO;
using Publisher.Repository.Interface;
using Publisher.Service.Implementation.Common;
using Publisher.Service.Interface;

namespace Publisher.Service.Implementation
{
    public class StoryService(IMapper mapper, IStoryRepository repository, IDistributedCache cache)
        : AbstractCrudService<Story, StoryRequestTO, StoryResponseTO>(mapper, repository), IStoryService
    {
        public override async Task<bool> Remove(int id)
        {
            await cache.RemoveAsync(GetRedisId(id));

            return await base.Remove(id);
        }

        public override async Task<StoryResponseTO> GetByID(int id)
        {
            var cacheResponse = await cache.GetStringAsync(GetRedisId(id));

            if (cacheResponse is null)
            {
                var response = await base.GetByID(id);
                await cache.SetStringAsync(GetRedisId(id), JsonConvert.SerializeObject(response));

                return response;
            }

            _ = Task.Run(async () =>
            {
                var res = await base.GetByID(id);
                await cache.SetStringAsync(GetRedisId(id), JsonConvert.SerializeObject(res));
            });

            return JsonConvert.DeserializeObject<StoryResponseTO>(cacheResponse)
                ?? throw new Exception("Unable to deserialize Story");
        }

        public override async Task<StoryResponseTO> Add(StoryRequestTO storyTo)
        {
            if (!Validate(storyTo))
            {
                throw new InvalidDataException("Story is not valid");
            }

            var res = await base.Add(storyTo);
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        public override async Task<StoryResponseTO> Update(StoryRequestTO storyTo)
        {
            if (!Validate(storyTo))
            {
                throw new InvalidDataException($"UPDATE invalid data: {storyTo}");
            }

            var res = await base.Update(storyTo);
            await cache.RemoveAsync(GetRedisId(res.Id));
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        private static string GetRedisId(int id) => $"Story:{id}";

        public Task<StoryResponseTO> GetStoryByParam(IList<string> markerNames, IList<int> markerIds, string editorLogin,
            string title, string content)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(StoryRequestTO story)
        {
            var titleLen = story.Title.Length;
            var contentLen = story.Content.Length;

            if (titleLen < 2 || titleLen > 64)
            {
                return false;
            }
            if (contentLen < 4 || contentLen > 2048)
            {
                return false;
            }
            if (story.Modified < story.Created)
            {
                return false;
            }
            return true;
        }
    }
}
