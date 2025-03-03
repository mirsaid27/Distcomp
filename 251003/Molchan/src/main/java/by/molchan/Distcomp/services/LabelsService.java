package by.molchan.Distcomp.services;


import by.molchan.Distcomp.DTOs.Requests.LabelRequestDTO;
import by.molchan.Distcomp.DTOs.Responses.LabelResponseDTO;
import by.molchan.Distcomp.models.Article;
import by.molchan.Distcomp.models.Label;
import by.molchan.Distcomp.repositories.ArticlesRepository;
import by.molchan.Distcomp.repositories.LabelsRepository;
import by.molchan.Distcomp.utils.exceptions.NotFoundException;
import by.molchan.Distcomp.utils.mappers.LabelsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelsService {
    private final LabelsRepository labelsRepository;
    private final ArticlesRepository articlesRepository;
    private final LabelsMapper labelsMapper;
    

    @Autowired
    public LabelsService(LabelsRepository labelsRepository, ArticlesRepository articlesRepository, LabelsMapper labelsMapper) {
        this.labelsRepository = labelsRepository;
        this.articlesRepository = articlesRepository;
        this.labelsMapper = labelsMapper;
    }

    public LabelResponseDTO save(Label label, long articleId) {
        Article article = articlesRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article with such id does not exist"));
        article.getLabels().add(label);
        label.getArticles().add(article);
        return labelsMapper.toLabelResponse(labelsRepository.save(label));
    }

    public LabelResponseDTO save(LabelRequestDTO labelRequestDTO) {
        Label label = labelsMapper.toLabel(labelRequestDTO);
        return labelsMapper.toLabelResponse(labelsRepository.save(label));
    }
    public List<LabelResponseDTO> findAll() {
        return labelsMapper.toLabelResponseList(labelsRepository.findAll());
    }

    public LabelResponseDTO findById(long id) {
        return labelsMapper.toLabelResponse(labelsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Label with such id does not exist")));
    }

    public void deleteById(long id) {
        if (!labelsRepository.existsById(id))
            throw new NotFoundException("Label with such id not found");
        labelsRepository.deleteById(id);
    }

    public LabelResponseDTO update(LabelRequestDTO labelRequestDTO) {
        Label label = labelsMapper.toLabel(labelRequestDTO);
        return labelsMapper.toLabelResponse(labelsRepository.save(label));
    }

    public boolean existsByName(String name){
        return labelsRepository.existsByName(name);
    }

}
