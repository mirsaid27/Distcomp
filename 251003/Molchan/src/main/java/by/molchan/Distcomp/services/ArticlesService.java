package by.molchan.Distcomp.services;


import by.molchan.Distcomp.DTOs.Requests.ArticleRequestDTO;
import by.molchan.Distcomp.DTOs.Responses.ArticleResponseDTO;
import by.molchan.Distcomp.models.Article;
import by.molchan.Distcomp.models.Label;
import by.molchan.Distcomp.models.Creator;
import by.molchan.Distcomp.repositories.ArticlesRepository;
import by.molchan.Distcomp.repositories.LabelsRepository;
import by.molchan.Distcomp.repositories.CreatorsRepository;
import by.molchan.Distcomp.utils.exceptions.NotFoundException;
import by.molchan.Distcomp.utils.mappers.ArticlesMapper;
import by.molchan.Distcomp.utils.mappers.LabelsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final CreatorsRepository creatorsRepository;
    private final ArticlesMapper articlesMapper;
    private final LabelsMapper labelsMapper;
    private LabelsRepository labelsRepository;

    @Autowired
    public ArticlesService(ArticlesRepository articlesRepository, CreatorsRepository creatorsRepository,
                           ArticlesMapper articlesMapper, LabelsMapper labelsMapper, LabelsRepository labelsRepository) {
        this.articlesRepository = articlesRepository;
        this.creatorsRepository = creatorsRepository;
        this.articlesMapper = articlesMapper;
        this.labelsMapper = labelsMapper;
        this.labelsRepository = labelsRepository;
    }

    private void setCreator(Article article, long creatorId){
        Creator creator = creatorsRepository.findById(creatorId).orElseThrow(() -> new NotFoundException("Creator with such id does not exist"));
        article.setCreator(creator);
    }

    @Transactional
    public ArticleResponseDTO save(ArticleRequestDTO articleRequestDTO) {
        Article article = articlesMapper.toArticle(articleRequestDTO);
        setCreator(article, articleRequestDTO.getCreatorId());
        if (articleRequestDTO.getLabels().size() > 0)
            saveLabels(article, articleRequestDTO.getLabels().stream().map(Label::new).toList());
        article.setCreated(new Date());
        article.setModified(new Date());
        return articlesMapper.toArticleResponse(articlesRepository.save(article));
    }

    private void saveLabels(Article article, List<Label> labels){
        Set<String> labelNames = labels.stream().map(Label::getName).collect(Collectors.toSet());
        List<Label> existingLabels = labelsRepository.findByNameIn(labelNames);


        Set<String> existingLabelNames = existingLabels.stream().map(Label::getName).collect(Collectors.toSet());
        List<Label> newLabels = labels.stream()
                .filter(label -> !existingLabelNames.contains(label.getName()))
                .collect(Collectors.toList());

        if (!newLabels.isEmpty()) {
            labelsRepository.saveAll(newLabels);
        }

        existingLabels.addAll(newLabels);

        article.setLabels(existingLabels);

        for (Label label : existingLabels) {
            label.getArticles().add(article);
        }
    }


    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> findAll() {
        return articlesMapper.toArticleResponseList(articlesRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ArticleResponseDTO findById(long id) {
        return articlesMapper.toArticleResponse(
                articlesRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Article with such id does not exist")));
    }

    @Transactional
    public void deleteById(long id) {
        if (!articlesRepository.existsById(id)) {
            throw new NotFoundException("Article not found");
        }
        articlesRepository.deleteById(id);
    }

    @Transactional
    public ArticleResponseDTO update(ArticleRequestDTO articleRequestDTO) {
        Article article = articlesMapper.toArticle(articleRequestDTO);
        Article oldArticle = articlesRepository.findById(article.getId()).orElseThrow(() -> new NotFoundException("Old article not found"));
        Long creatorId = articleRequestDTO.getCreatorId();

        if (creatorId != null) {
            setCreator(article, creatorId);
        }
        article.setCreated(oldArticle.getCreated());
        article.setModified(new Date());
        return articlesMapper.toArticleResponse(articlesRepository.save(article));
    }

    public boolean existsByTitle(String title){
        return articlesRepository.existsByTitle(title);
    }
}
