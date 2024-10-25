package com.example.discussion.mapper;

import com.example.discussion.model.Comment;
import com.example.discussion.model.dto.CommentRequestTo;
import com.example.discussion.model.dto.CommentResponseTo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T21:30:50+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toEntity(CommentRequestTo commentRequestTo) {
        if ( commentRequestTo == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setStoryId( commentRequestTo.getStoryId() );
        comment.setCountry( commentRequestTo.getCountry() );
        comment.setId( commentRequestTo.getId() );
        comment.setContent( commentRequestTo.getContent() );

        return comment;
    }

    @Override
    public CommentResponseTo toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseTo commentResponseTo = new CommentResponseTo();

        commentResponseTo.setStoryId( comment.getStoryId() );
        commentResponseTo.setCountry( comment.getCountry() );
        commentResponseTo.setId( comment.getId() );
        commentResponseTo.setContent( comment.getContent() );

        return commentResponseTo;
    }
}
