package by.bsuir.distcomp.service;

import by.bsuir.distcomp.dto.mapper.ArticleMapper;
import by.bsuir.distcomp.dto.mapper.AuthorMapper;
import by.bsuir.distcomp.dto.request.ArticleRequestTo;
import by.bsuir.distcomp.dto.response.ArticleResponseTo;
import by.bsuir.distcomp.entity.Article;
import by.bsuir.distcomp.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public List<ArticleResponseTo> getAllArticles() {
        return articleRepository.findAll().stream().map(articleMapper::toDto).toList();
    }

    public ArticleResponseTo getArticleById(Long id) {
        return articleMapper.toDto(articleRepository.findById(id));
    }

    public ArticleResponseTo createArticle(ArticleRequestTo articleRequestTo) {
        return articleMapper.toDto(articleRepository.create(articleMapper.toEntity(articleRequestTo)));
    }

    public ArticleResponseTo updateArticle(ArticleRequestTo articleRequestTo) {
        return articleMapper.toDto(articleRepository.update(articleMapper.toEntity(articleRequestTo)));
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
    
}
