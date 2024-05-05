package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.News;
import by.bsuir.publisher.model.request.NewsRequestTo;
import by.bsuir.publisher.model.response.NewsResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface NewsMapper {
    @Mapping(target = "editorId", source = "editor.id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    NewsResponseTo getResponseTo(News news);

    List<NewsResponseTo> getListResponseTo(Iterable<News> news);

    @Mapping(target = "editor", source = "editorId", qualifiedByName = "editorIdToEditorRef")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    News getNews(NewsRequestTo newsRequestTo);
}
