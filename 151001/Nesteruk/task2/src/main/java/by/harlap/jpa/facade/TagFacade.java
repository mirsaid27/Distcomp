package by.harlap.jpa.facade;

import by.harlap.jpa.dto.request.CreateTagDto;
import by.harlap.jpa.dto.request.UpdateTagDto;
import by.harlap.jpa.dto.response.TagResponseDto;
import by.harlap.jpa.mapper.TagMapper;
import by.harlap.jpa.model.Tag;
import by.harlap.jpa.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagFacade {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @Transactional(readOnly = true)
    public TagResponseDto findById(Long id) {
        Tag tag = tagService.findById(id);
        return tagMapper.toTagResponse(tag);
    }

    @Transactional(readOnly = true)
    public List<TagResponseDto> findAll() {
        List<Tag> tags = tagService.findAll();

        return tags.stream().map(tagMapper::toTagResponse).toList();
    }

    @Transactional
    public TagResponseDto save(CreateTagDto tagRequest) {
        Tag tag = tagMapper.toTag(tagRequest);

        Tag savedTag = tagService.save(tag);

        return tagMapper.toTagResponse(savedTag);
    }

    @Transactional
    public TagResponseDto update(UpdateTagDto tagRequest) {
        Tag tag = tagService.findById(tagRequest.getId());

        Tag updatedTag = tagMapper.toTag(tagRequest, tag);

        return tagMapper.toTagResponse(tagService.update(updatedTag));
    }

    @Transactional
    public void delete(Long id) {
        tagService.deleteById(id);
    }
}
