package org.example.publisher.impl.tag.mapper.Impl;

import org.example.publisher.impl.tag.Tag;
import org.example.publisher.impl.tag.mapper.TagMapper;
import org.example.publisher.impl.tag.dto.TagRequestTo;
import org.example.publisher.impl.tag.dto.TagResponseTo;
import org.example.publisher.impl.tweet.Tweet;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TagMapperImpl implements TagMapper {
    @Override
    public TagRequestTo tagToRequestTo(Tag tag) {
        List<BigInteger> ids = new ArrayList<>();
        for (var item: tag.getTweets()) {
            ids.add(item.getId());
        }

        return new TagRequestTo(
                tag.getId(),
                tag.getName(),
                ids
        );
    }

    @Override
    public List<TagRequestTo> tagToRequestTo(Iterable<Tag> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Tag dtoToEntity(TagRequestTo tagRequestTo, List<Tweet> twets) {
        return new Tag(
                tagRequestTo.getId(),
                tagRequestTo.getName(),
                twets
        );
    }

    @Override
    public TagResponseTo tagToResponseTo(Tag tag) {
        List<BigInteger> ids = new ArrayList<>();
        for (var item: tag.getTweets()) {
            ids.add(item.getId());
        }

        return new TagResponseTo(
                tag.getId(),
                tag.getName(),
                ids
        );
    }

    @Override
    public List<TagResponseTo> tagToResponseTo(Iterable<Tag> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToResponseTo)
                .collect(Collectors.toList());
    }
}
