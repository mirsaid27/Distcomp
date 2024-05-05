package by.bsuir.egor.dto;

import java.time.LocalDateTime;

public class IssueRequestTo {
    private long id;

    private long editorId;

    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public void setEditorId(long editorId) {
        this.editorId = editorId;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public long getEditorId() {
        return editorId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
