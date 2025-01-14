package by.bsuir.dc.impl.label;

import by.bsuir.dc.api.base.AbstractMemoryRepository;
import by.bsuir.dc.impl.label.model.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LabelRepository extends AbstractMemoryRepository<Label> {
    @Override
    public Optional<Label> update(Label label) {
        long id = label.getId();
        if(Objects.isNull(map.get(id))) {
            throw new NoSuchElementException("update failed");
        }
        Label memoryLabel = map.get(id);
        Optional.of(label.getName()).ifPresent(memoryLabel::setName);

        map.put(id, memoryLabel);
        return Optional.of(memoryLabel);
    }
    @Override
    public boolean deleteById(long id) {
        return map.remove(id) != null;
    }
}
