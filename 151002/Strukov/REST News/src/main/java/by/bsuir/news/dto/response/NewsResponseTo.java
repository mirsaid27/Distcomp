package by.bsuir.news.dto.response;

import by.bsuir.news.entity.News;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.sql.Timestamp;
import java.util.Date;

public class NewsResponseTo implements IResponseTO<News, NewsResponseTo> {
    private Long id;
    private String title;
    private String content;
    private Long editorId;
    private Date created;
    private Date modified;

    public NewsResponseTo toModel(News source) {
        return toResponse(source);
    }

    public static NewsResponseTo toResponse(News source) {
        NewsResponseTo model = new NewsResponseTo();
        model.id = source.getId();
        model.title = source.getTitle();
        model.content = source.getContent();
        model.created = source.getCreated();
        model.modified = source.getModified();
        model.editorId = source.getEditorId().getId();
        return model;
    }

    public NewsResponseTo() {

    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }
}
