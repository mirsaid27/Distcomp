using Domain.Entities;
using Domain.Exceptions;
using Domain.Repository;

namespace Infrastructure.Repositories;

public class EditorRepository : IEditorRepository
{
    private readonly Dictionary<long, Editor> _editors = new();
    private long _nextId = 1;

    public EditorRepository()
    {
        _ = AddEditor(new Editor
        {
            Login = "karasandrey2005@gmail.com",
            Firstname = "Андрей",
            Lastname = "Карась"
        });
    }
    
    public async Task<Editor?> AddEditor(Editor editor)
    {
        if (_editors.Values.All(obj => obj.Login != editor.Login))
        {
            editor.Id = _nextId;
            _editors.Add(_nextId,editor);
            return _editors[_nextId++];
        }
        else
        {
            throw new BadRequestException("Such login already exists", new Dictionary<string, string[]>());
        }
    }

    public async Task<Editor?> GetEditor(long editorId)
    {
        if (_editors.TryGetValue(editorId, out var editor))
        {
            return editor;
        }

        throw new NotFoundException("Id",$"{editorId}");
    }
    public async Task<Editor?> RemoveEditor(long editorId)
    {
        if (_editors.Remove(editorId, out var editor))
        {
            return editor;
        }
        throw new NotFoundException("Id",$"{editorId}");
    }

    public async Task<Editor?> UpdateEditor(Editor editor)
    {
        if (_editors.ContainsKey(editor.Id))
        {
            if (_editors.Values.All(obj => obj.Login != editor.Login))
            {
                _editors[editor.Id] = editor;
                return _editors[editor.Id];
            }
            else
            {
                throw new BadRequestException("Such login already exists", new Dictionary<string, string[]>());
            }
        }
        throw new NotFoundException("Id",$"{editor.Id}");
    }
    
    public async Task<IEnumerable<Editor?>?> GetAllEditors()
    {
        return _editors.Values.ToList();
    }
}