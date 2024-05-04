package by.harlap.rest.facade;

import by.harlap.rest.dto.request.CreateTagDto;
import by.harlap.rest.dto.request.UpdateTagDto;
import by.harlap.rest.dto.response.TagResponseDto;
import by.harlap.rest.mapper.TagMapper;
import by.harlap.rest.model.Tag;
import by.harlap.rest.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagFacade {

    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagResponseDto findById(Long id) {
        Tag tag = tagService.findById(id);
        return tagMapper.toTagResponse(tag);
    }

    public List<TagResponseDto> findAll() {
        List<Tag> tags = tagService.findAll();

        return tags.stream().map(tagMapper::toTagResponse).toList();
    }

    public TagResponseDto save(CreateTagDto tagRequest) {
        Tag tag = tagMapper.toTag(tagRequest);

        Tag savedTag = tagService.save(tag);

        return tagMapper.toTagResponse(savedTag);
    }

    public TagResponseDto update(UpdateTagDto tagRequest) {
        Tag tag = tagService.findById(tagRequest.getId());

        Tag updatedTag = tagMapper.toTag(tagRequest, tag);

        return tagMapper.toTagResponse(tagService.update(updatedTag));
    }

    public void delete(Long id) {
        tagService.deleteById(id);
    }
}
