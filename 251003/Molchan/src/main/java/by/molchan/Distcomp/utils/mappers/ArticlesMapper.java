package by.molchan.Distcomp.utils.mappers;

import by.molchan.Distcomp.DTOs.Requests.ArticleRequestDTO;
import by.molchan.Distcomp.DTOs.Responses.ArticleResponseDTO;
import by.molchan.Distcomp.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArticlesMapper {

    @Mapping(target = "creatorId", expression = "java(article.getCreator().getId())")
    @Mapping(target = "labels", expression = "java(article.getLabels().stream().map(label -> label.getName()).toList())")
    ArticleResponseDTO toArticleResponse(Article article);

    List<ArticleResponseDTO> toArticleResponseList(List<Article> articles);

    @Mapping(target = "labels", ignore = true)
    Article toArticle(ArticleRequestDTO articleRequestDTO);
}
