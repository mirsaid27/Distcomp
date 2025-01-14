package by.bsuir.dc.impl.label;
import by.bsuir.dc.api.RestService;
import by.bsuir.dc.impl.label.model.Label;
import by.bsuir.dc.impl.label.model.LabelRequest;
import by.bsuir.dc.impl.label.model.LabelResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
@AllArgsConstructor
public class LabelService implements RestService<LabelRequest, LabelResponse> {
    private by.bsuir.dc.impl.label.LabelMapper labelMapper;
    private LabelRepository labelCrudRepository;

    @Override
    public List<LabelResponse> findAll() {
        Iterable<Label> labels = labelCrudRepository.getAll();
        return labelMapper.toLabelResponseDto(labels);
    }
    @Override
    public LabelResponse findById(long id) {
        Label label = labelCrudRepository.getBy(id).orElseThrow();
        return labelMapper.toLabelResponseDto(label);
    }
    @Override
    public LabelResponse create(LabelRequest request) {
        return labelCrudRepository
                .save(labelMapper.toLabelDto(request))
                .map(labelMapper::toLabelResponseDto)
                .orElseThrow();
    }
    @Override
    public LabelResponse update(LabelRequest request) {
        return labelCrudRepository
                .update(labelMapper.toLabelDto(request))
                .map(labelMapper::toLabelResponseDto)
                .orElseThrow();
    }
    @Override
    public boolean removeById(long id) {
        if (!labelCrudRepository.deleteById(id)) {
            throw new NoSuchElementException("Element not found");
        }
        return true;
    }
}
