package org.example.discussionservice.service;

import org.example.discussionservice.dto.PostDto;
import org.example.discussionservice.kafkacl.request.KafkaRequestType;
import org.example.discussionservice.kafkacl.response.KafkaRequestProcessor;
import org.example.discussionservice.model.Post;
import org.example.discussionservice.util.ObjectMapperProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final Map<KafkaRequestType, KafkaRequestProcessor> processors;

    private final String ERROR_MESSAGE = "ERROR_MESSAGE";

    @Override
    public Map<KafkaRequestType, KafkaRequestProcessor> getProcessors() {
        return processors;
    }

    private final PostService postService;

    @Autowired
    public MessageServiceImpl(PostService postService) {
        processors = new HashMap<>();
        processors.put(KafkaRequestType.POST, this::createPost);
        processors.put(KafkaRequestType.GET_ALL, value -> getAllPosts());
        processors.put(KafkaRequestType.GET, this::getPostById);
        processors.put(KafkaRequestType.PUT, this::updatePost);
        processors.put(KafkaRequestType.DELETE, this::deletePostById);

        this.postService = postService;
    }

    private String getPostById(String argument) {
        Long id = ObjectMapperProvider.parseJsonStringToObject(argument, Long.class);
        PostDto postDto = postService.getPostDtoById(id);
        return Optional.ofNullable(postDto)
                .map(ObjectMapperProvider::writeObjectAsString)
                .orElse(ERROR_MESSAGE);
    }

    private String getAllPosts() {
        return Optional.of(postService.getAllPostDtos())
                .map(ObjectMapperProvider::writeObjectAsString)
                .orElse("[]");
    }

    private String createPost(String argument) {

        PostDto requestPostDto = ObjectMapperProvider.parseJsonStringToObject(argument, PostDto.class);
        PostDto responsePostDto = postService.addPost(requestPostDto);
        return Optional.ofNullable(responsePostDto)
                .map(ObjectMapperProvider::writeObjectAsString)
                .orElse(ERROR_MESSAGE);
    }

    private String updatePost(String argument) {

        PostDto requestPostDto = ObjectMapperProvider.parseJsonStringToObject(argument, PostDto.class);
        PostDto responsePostDto = postService.updatePost(requestPostDto);
        return Optional.ofNullable(responsePostDto)
                .map(ObjectMapperProvider::writeObjectAsString)
                .orElse(ERROR_MESSAGE);
    }

    private String deletePostById(String argument) {
        Long id = ObjectMapperProvider.parseJsonStringToObject(argument, Long.class);

        PostDto postDto = postService.deletePostById(id);
        return (postDto != null) ? ObjectMapperProvider.writeObjectAsString(postDto) : ERROR_MESSAGE;
    }
}
