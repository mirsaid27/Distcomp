package by.bsuir.egor.Service;

import by.bsuir.egor.Entity.Editor;
import by.bsuir.egor.dto.*;
import by.bsuir.egor.redis.RedisEditorRepository;
import by.bsuir.egor.repositories.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EditorService implements IService<EditorResponseTo, EditorRequestTo> {

    private final EditorRepository editorRepository;

    @Autowired
    public EditorService(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }
    
    @Autowired
    private RedisEditorRepository redisRepository;

    public List<EditorResponseTo> getAll() {
        List<Editor> editorList = redisRepository.getAll();;

        List<EditorResponseTo> resultList = new ArrayList<>();
        if(!editorList.isEmpty())
        {
            for (int i = 0; i < editorList.size(); i++) {
                resultList.add(EditorMapper.INSTANCE.editorToEditorResponseTo(editorList.get(i)));
            }
        }else
        {
            editorList = editorRepository.findAll();
            for (int i = 0; i < editorList.size(); i++) {
                resultList.add(EditorMapper.INSTANCE.editorToEditorResponseTo(editorList.get(i)));
                redisRepository.add(editorList.get(i));
            }
        }
        return resultList;
    }

    public EditorResponseTo update(EditorRequestTo updatingUser) {
        Editor editor = EditorMapper.INSTANCE.editorRequestToToEditor(updatingUser);
        if (validateEditor(editor)) {
            EditorResponseTo responseTo;
            Optional<Editor> redisEditor = redisRepository.getById(editor.getId());
            if(redisEditor.isPresent() && editor.equals(redisEditor.get()))
            {
                return EditorMapper.INSTANCE.editorToEditorResponseTo(redisEditor.get());
            }
            try {
                Editor result = editorRepository.save(editor);
                redisRepository.update(result);
                responseTo = EditorMapper.INSTANCE.editorToEditorResponseTo(result);
            }catch (Exception e)
            {
                e.getMessage();
                return new EditorResponseTo();
            }
            return responseTo;
        } else return new EditorResponseTo();
    }

    public EditorResponseTo getById(long id) {
        Optional<Editor> redisEditor = redisRepository.getById(id);
        Editor result;
        if(redisEditor.isPresent())
        {
            return EditorMapper.INSTANCE.editorToEditorResponseTo(redisEditor.get());
        }
        else {
            result = editorRepository.findById(id);
            redisRepository.add(result);
        }
        return EditorMapper.INSTANCE.editorToEditorResponseTo(result);
    }

    public boolean deleteById(long id) {
        if(editorRepository.existsById(id)) {
            editorRepository.deleteById(id);
            redisRepository.delete(id);
            return true;
        }return false;

    }

    public ResponseEntity<EditorResponseTo> add(EditorRequestTo editorRequestTo) {
        Editor editor = EditorMapper.INSTANCE.editorRequestToToEditor(editorRequestTo);
        if (validateEditor(editor)) {
            EditorResponseTo responseTo;
            try {
                Editor result = editorRepository.save(editor);
                redisRepository.add(result);
                responseTo = EditorMapper.INSTANCE.editorToEditorResponseTo(result);
            } catch (Exception e)
            {
                editor.setId(editor.getId()-1);
                return new ResponseEntity<>(EditorMapper.INSTANCE.editorToEditorResponseTo(editor), HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(responseTo, HttpStatus.CREATED);
        } else return new ResponseEntity<>(EditorMapper.INSTANCE.editorToEditorResponseTo(editor), HttpStatus.FORBIDDEN);
    }

    private boolean validateEditor(Editor editor) {
        if(editor.getFirstname()!=null && editor.getLastname()!=null && editor.getLogin()!=null && editor.getPassword()!=null) {
            String firstname = editor.getFirstname();
            String lastname = editor.getLastname();
            String login = editor.getLogin();
            String password = editor.getPassword();
            if ((firstname.length() >= 2 && firstname.length() <= 64) &&
                    (lastname.length() >= 2 && lastname.length() <= 64) &&
                    (password.length() >= 8 && firstname.length() <= 128) &&
                    (login.length() >= 2 && login.length() <= 64)) return true;
        }
        return false;
    }
}
