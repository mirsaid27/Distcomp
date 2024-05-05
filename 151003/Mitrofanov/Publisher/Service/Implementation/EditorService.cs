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
    public class EditorService(IMapper mapper, IEditorRepository repository, IDistributedCache cache) :
        AbstractCrudService<Editor, EditorRequestTO, EditorResponseTO>(mapper, repository), IEditorService
    {
        private readonly IMapper _mapper = mapper;

        public override async Task<bool> Remove(int id)
        {
            await cache.RemoveAsync(GetRedisId(id));

            return await base.Remove(id);
        }

        public override async Task<EditorResponseTO> GetByID(int id)
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

            return JsonConvert.DeserializeObject<EditorResponseTO>(cacheResponse)
                ?? throw new Exception("Unable to deserialize Editor");
        }

        public override async Task<EditorResponseTO> Add(EditorRequestTO editorTo)
        {
            if (!Validate(editorTo))
            {
                throw new InvalidDataException("Editor is not valid");
            }

            var res = await base.Add(editorTo);
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        public override async Task<EditorResponseTO> Update(EditorRequestTO editorTo)
        {
            if (!Validate(editorTo))
            {
                throw new InvalidDataException($"UPDATE invalid data: {editorTo}");
            }

            var res = await base.Update(editorTo);
            await cache.RemoveAsync(GetRedisId(res.Id));
            await cache.SetStringAsync(GetRedisId(res.Id), JsonConvert.SerializeObject(res));

            return res;
        }

        private static string GetRedisId(int id) => $"Editor:{id}";

        public async Task<EditorResponseTO> GetByStoryID(int storyId)
        {
            var response = await repository.GetByStoryIdAsync(storyId);

            return _mapper.Map<EditorResponseTO>(response.Editor);
        }

        private static bool Validate(EditorRequestTO editor)
        {
            var fnameLen = editor.FirstName.Length;
            var lnameLen = editor.LastName.Length;
            var passLen = editor.Password.Length;
            var loginLen = editor.Login.Length;

            if (fnameLen < 2 || fnameLen > 64)
            {
                return false;
            }
            if (lnameLen < 2 || fnameLen > 64)
            {
                return false;
            }
            if (passLen < 8 || passLen > 128)
            {
                return false;
            }
            if (loginLen < 2 || loginLen > 64)
            {
                return false;
            }
            return true;
        }
    }
}
