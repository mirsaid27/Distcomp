package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.dao.EditorRepository;
import by.bsuir.publisher.dao.NewsRepository;
import by.bsuir.publisher.model.entity.Editor;
import by.bsuir.publisher.model.entity.News;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final NewsRepository newsRepository;
    private final EditorRepository editorRepository;

    @Named("newsIdToNewsRef")
    public News newsIdToNewsRef(Long newsId) {
        return newsRepository.getReferenceById(newsId);
    }

    @Named("editorIdToEditorRef")
    public Editor editorIdToEditorRef(Long editorId) {
        return editorRepository.getReferenceById(editorId);
    }
}
