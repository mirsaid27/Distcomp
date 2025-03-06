package by.bsuir.distcomp.dto.mapper;

import by.bsuir.distcomp.dto.request.ArticleRequestTo;
import by.bsuir.distcomp.dto.response.ArticleResponseTo;
import by.bsuir.distcomp.entity.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleResponseTo toDto(Article article);
    Article toEntity(ArticleRequestTo articleDTO);
}
