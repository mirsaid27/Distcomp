package by.bsuir.poit.dc.rest.api.dto.mappers.config;

import by.bsuir.poit.dc.rest.dao.StickerRepository;
import by.bsuir.poit.dc.rest.dao.TweetRepository;
import by.bsuir.poit.dc.rest.dao.EditorRepository;
import by.bsuir.poit.dc.rest.model.Sticker;
import by.bsuir.poit.dc.rest.model.Tweet;
import by.bsuir.poit.dc.rest.model.Editor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MapperConfig;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Name Surname
 * 
 */
@MapperConfig
@Component
@RequiredArgsConstructor
public class TweetMapperConfig {
    private final TweetRepository tweetRepository;
    private final EditorRepository editorRepository;
    private final StickerRepository stickerRepository;

    @Named("getEditorRef")
    public Editor getEditorRef(long editorId) {
	return editorRepository.getReferenceById(editorId);
    }

    @Named("getTweetRef")
    public Tweet getTweetRef(long tweetId) {
	return tweetRepository.getReferenceById(tweetId);
    }

    @Named("getCommentRef")
    public Sticker getCommentRef(long commentId) {
	return stickerRepository.getReferenceById(commentId);
    }

    @Named("mapDate")
    public Date mapDate(Timestamp timestamp) {
	return new Date(timestamp.getTime());
    }
}
