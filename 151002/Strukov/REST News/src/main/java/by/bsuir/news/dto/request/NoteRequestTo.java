package by.bsuir.news.dto.request;

import by.bsuir.news.entity.Note;
import org.hibernate.validator.constraints.Length;

public class NoteRequestTo {
    private Long id;
    @Length(min = 2, max = 2048)
    private String content;
    private Long newsId;
    public NoteRequestTo() {

    }
    public static Note fromRequest(NoteRequestTo request) {
        Note note = new Note();
        note.setId(request.id);
        note.setContent(request.content);
        return note;
    }
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
