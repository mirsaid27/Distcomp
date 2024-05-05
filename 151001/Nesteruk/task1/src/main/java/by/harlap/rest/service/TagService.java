package by.harlap.rest.service;

import by.harlap.rest.model.Tag;

import java.util.List;

public interface TagService {

    Tag findById(Long id);

    void deleteById(Long id);

    Tag save(Tag tag);

    Tag update(Tag tag);

    List<Tag> findAll();
}
