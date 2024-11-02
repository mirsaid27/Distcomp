package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.Story;
import com.padabied.dc_rest.model.User;
import com.padabied.dc_rest.model.dto.requests.StoryRequestTo;
import com.padabied.dc_rest.model.dto.responses.StoryResponseTo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T21:44:20+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class StoryMapperImpl implements StoryMapper {

    @Override
    public StoryResponseTo toResponse(Story story) {
        if ( story == null ) {
            return null;
        }

        StoryResponseTo storyResponseTo = new StoryResponseTo();

        storyResponseTo.setUserId( storyUserId( story ) );
        storyResponseTo.setId( story.getId() );
        storyResponseTo.setTitle( story.getTitle() );
        storyResponseTo.setContent( story.getContent() );

        return storyResponseTo;
    }

    @Override
    public Story toEntity(StoryRequestTo storyRequestDto) {
        if ( storyRequestDto == null ) {
            return null;
        }

        Story story = new Story();

        story.setTitle( storyRequestDto.getTitle() );
        story.setContent( storyRequestDto.getContent() );

        return story;
    }

    private Long storyUserId(Story story) {
        if ( story == null ) {
            return null;
        }
        User user = story.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
