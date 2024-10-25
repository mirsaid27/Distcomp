package com.padabied.dc_rest.mapper;

import com.padabied.dc_rest.model.Comment;
import com.padabied.dc_rest.model.Story;
import com.padabied.dc_rest.model.dto.requests.CommentRequestTo;
import com.padabied.dc_rest.model.dto.responses.CommentResponseTo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T21:44:20+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentResponseTo toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseTo commentResponseTo = new CommentResponseTo();

        commentResponseTo.setStoryId( commentStoryId( comment ) );
        commentResponseTo.setId( comment.getId() );
        commentResponseTo.setContent( comment.getContent() );
        commentResponseTo.setCountry( comment.getCountry() );

        return commentResponseTo;
    }

    @Override
    public Comment toEntity(CommentRequestTo commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( commentRequestDto.getContent() );
        comment.setCountry( commentRequestDto.getCountry() );

        return comment;
    }

    private Long commentStoryId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Story story = comment.getStory();
        if ( story == null ) {
            return null;
        }
        Long id = story.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
