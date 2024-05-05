package by.bsuir.egor.storage;

import by.bsuir.egor.Entity.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class EditorRepository implements InMemoryRepository<Editor> {

    Map<Long, Editor> editorMemory = new ConcurrentHashMap<>();

    AtomicLong lastId = new AtomicLong();

    public EditorRepository() {}

    @Override
    public Editor findById(long id) {
        Editor editor = editorMemory.get(id);
        return editor;
    }

    @Override
    public List<Editor> findAll() {
        List<Editor> editorList = new ArrayList<>();
        for (Long key : editorMemory.keySet()) {
            Editor editor = editorMemory.get(key);

            editorList.add(editor);
        }
        return editorList;
    }

    @Override
    public Editor deleteById(long id) {
        Editor result = editorMemory.remove(id);
        return result;
    }

    @Override
    public boolean deleteAll() {
        editorMemory.clear();
        return true;
    }

    @Override
    public Editor insert(Editor insertObject) {
        long id = lastId.getAndIncrement();
        insertObject.setId(id);
        editorMemory.put(id, insertObject);
        return insertObject;
    }

    @Override
    public boolean updateById(Long id, Editor replacingEditor) {
        boolean status = editorMemory.replace(id, editorMemory.get(id), replacingEditor);
        return status;
    }

    @Override
    public boolean update(Editor updatingValue) {
        boolean status = editorMemory.replace(updatingValue.getId(), editorMemory.get(updatingValue.getId()), updatingValue);
        return status;
    }

}
