package com.bsuir.dc.service;

import com.bsuir.dc.dao.InMemoryTopicDao;
import com.bsuir.dc.dto.request.TopicRequestTo;
import com.bsuir.dc.dto.response.TopicResponseTo;
import com.bsuir.dc.exception.EntityNotFoundException;
import com.bsuir.dc.model.Topic;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final ModelMapper modelMapper;
    private final InMemoryTopicDao topicDao;

    @Autowired
    public TopicService(final ModelMapper modelMapper, final InMemoryTopicDao topicDao) {
        this.modelMapper = modelMapper;
        this.topicDao = topicDao;
    }

    private Topic convertToTopic(TopicRequestTo topicRequestTo) {
        return this.modelMapper.map(topicRequestTo, Topic.class);
    }

    private TopicResponseTo convertToResponse(Topic topic) {
        return this.modelMapper.map(topic, TopicResponseTo.class);
    }

    public TopicResponseTo create(TopicRequestTo topicRequestTo) {
        Topic topic = convertToTopic(topicRequestTo);
        topic.setCreated(new Date());
        topic.setModified(topic.getCreated());
        topicDao.save(topic);

        return convertToResponse(topic);
    }

    public List<TopicResponseTo> findAll() {
        return topicDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public TopicResponseTo findById(long id) throws EntityNotFoundException {
        Topic topic = topicDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This topic doesn't exist."));
        return convertToResponse(topic);
    }

    public TopicResponseTo update(TopicRequestTo topicRequestTo) throws EntityNotFoundException{
        Topic existingTopic = topicDao.findById(topicRequestTo.getId()).orElseThrow(()
                -> new EntityNotFoundException("This topic doesn't exist."));

        Topic updatedTopic = convertToTopic(topicRequestTo);
        updatedTopic.setId(topicRequestTo.getId());
        updatedTopic.setCreated(existingTopic.getCreated());
        updatedTopic.setModified(new Date());
        topicDao.save(updatedTopic);

        return convertToResponse(updatedTopic);
    }

    public TopicResponseTo partialUpdate(TopicRequestTo topicRequestTo) throws EntityNotFoundException{
        Topic topic = topicDao.findById(topicRequestTo.getId()).orElseThrow(()
                -> new EntityNotFoundException("This topic doesn't exist."));

        if (topicRequestTo.getContent() != null) {
            topic.setContent(topicRequestTo.getContent());
        }
        if (topicRequestTo.getTitle() != null) {
            topic.setTitle(topicRequestTo.getTitle());
        }
        if (topicRequestTo.getAuthorId() != 0) {
            topic.setAuthorId(topicRequestTo.getAuthorId());
        }

        topic.setModified(new Date());
        topicDao.save(topic);

        return convertToResponse(topic);
    }

    public void delete(long id) throws EntityNotFoundException {
        topicDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This topic doesn't exist."));
        topicDao.deleteById(id);
    }
}
