package org.example.dc.model;

import org.springframework.stereotype.Service;

@Service
public class Converter {
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLastname(user.getLastname());
        userDto.setFirstname(user.getFirstname());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLastname(userDto.getLastname());
        user.setFirstname(userDto.getFirstname());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public Story convert(StoryDto storyDto) {
        Story story = new Story();
        story.setTitle(storyDto.getTitle());
        story.setUser_id(storyDto.getUserId());
        story.setContent(storyDto.getContent());
        story.setCreated(storyDto.getCreated());
        story.setModified(storyDto.getModified());
        story.setId(storyDto.getId());
        return story;
    }

    public StoryDto convert(Story story) {
        StoryDto storyDto = new StoryDto();
        storyDto.setTitle(story.getTitle());
        storyDto.setUserId(story.getUser_id());
        storyDto.setContent(story.getContent());
        storyDto.setCreated(story.getCreated());
        storyDto.setModified(story.getModified());
        storyDto.setId(story.getId());
        return storyDto;
    }

    public CommentDto convert(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setStoryId(comment.getStory_id());
        return commentDto;
    }

    public Comment convert(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setStory_id(commentDto.getStoryId());
        return comment;
    }

    public Marker convert(MarkerDto markerDto) {
        Marker marker = new Marker();
        marker.setName(markerDto.getName());
        marker.setId(markerDto.getId());
        return marker;
    }

    public MarkerDto convert(Marker marker) {
        MarkerDto markerDto = new MarkerDto();
        markerDto.setName(marker.getName());
        markerDto.setId(marker.getId());
        return markerDto;
    }
}