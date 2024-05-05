package by.bsuir.services;

import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.entities.Label;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.LabelListMapper;
import by.bsuir.mapper.LabelMapper;
import by.bsuir.repository.LabelRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@CacheConfig(cacheNames = "labelCache")
public class LabelService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    LabelRepository labelDao;
    @Autowired
    LabelListMapper labelListMapper;
    @Cacheable(cacheNames = "labels", key = "#id", unless = "#result == null")
    public LabelResponseTo getLabelById(@Min(0) Long id) throws NotFoundException {
        Optional<Label> label = labelDao.findById(id);
        return label.map(value -> labelMapper.labelToLabelResponse(value)).orElseThrow(() -> new NotFoundException("Label not found!", 40004L));
    }
    @Cacheable(cacheNames = "labels")
    public List<LabelResponseTo> getLabels(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Label> labels = labelDao.findAll(pageable);
        return labelListMapper.toLabelResponseList(labels.toList());
    }
    @CacheEvict(cacheNames = "labels", allEntries = true)
    public LabelResponseTo saveLabel(@Valid LabelRequestTo label) {
        Label labelToSave = labelMapper.labelRequestToLabel(label);
        return labelMapper.labelToLabelResponse(labelDao.save(labelToSave));
    }
    @Caching(evict = { @CacheEvict(cacheNames = "labels", key = "#id"),
            @CacheEvict(cacheNames = "labels", allEntries = true) })
    public void deleteLabel(@Min(0) Long id) throws DeleteException {
        if (!labelDao.existsById(id)) {
            throw new DeleteException("Label not found!", 40004L);
        } else {
            labelDao.deleteById(id);
        }
    }
    @CacheEvict(cacheNames = "labels", allEntries = true)
    public LabelResponseTo updateLabel(@Valid LabelRequestTo label) throws UpdateException {
        Label labelToUpdate = labelMapper.labelRequestToLabel(label);
        if (!labelDao.existsById(labelToUpdate.getId())) {
            throw new UpdateException("Label not found!", 40004L);
        } else {
            return labelMapper.labelToLabelResponse(labelDao.save(labelToUpdate));
        }
    }

    public List<LabelResponseTo> getLabelByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Label> label = labelDao.findLabelsByIssueId(issueId);
        return labelListMapper.toLabelResponseList(label);
    }
}
