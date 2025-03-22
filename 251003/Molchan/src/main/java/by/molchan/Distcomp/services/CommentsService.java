package by.molchan.Distcomp.services;


import by.molchan.Distcomp.DTOs.Requests.CommentRequestDTO;
import by.molchan.Distcomp.DTOs.Responses.CommentResponseDTO;
import by.molchan.Distcomp.models.Article;
import by.molchan.Distcomp.models.Comment;
import by.molchan.Distcomp.repositories.ArticlesRepository;
import by.molchan.Distcomp.repositories.CommentsRepository;
import by.molchan.Distcomp.utils.exceptions.NotFoundException;
import by.molchan.Distcomp.utils.mappers.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final ArticlesRepository articlesRepository;
    private final CommentsMapper commentsMapper;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, ArticlesRepository articlesRepository, CommentsMapper commentsMapper) {
        this.commentsRepository = commentsRepository;
        this.articlesRepository = articlesRepository;
        this.commentsMapper = commentsMapper;
    }

    private void setArticle(Comment comment, long articleId){
        Article article = articlesRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article with such id does not exist"));
        comment.setArticle(article);
    }
    public CommentResponseDTO save(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentsMapper.toComment(commentRequestDTO);
        setArticle(comment, commentRequestDTO.getArticleId());
        return commentsMapper.toCommentResponse(commentsRepository.save(comment));
    }

    public List<CommentResponseDTO> findAll() {
        return commentsMapper.toCommentResponseList(commentsRepository.findAll());
    }

    public CommentResponseDTO findById(long id) {
        return commentsMapper.toCommentResponse(commentsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with such id does not exist")));
    }

    public void deleteById(long id) {
        if (!commentsRepository.existsById(id)) {
            throw new NotFoundException("Comment not found");
        }
        commentsRepository.deleteById(id);
    }

    public CommentResponseDTO update(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentsMapper.toComment(commentRequestDTO);
        Long articleId = commentRequestDTO.getArticleId();
        if (articleId != null) {
            setArticle(comment, articleId);
        }
        return commentsMapper.toCommentResponse(commentsRepository.save(comment));
    }

}
