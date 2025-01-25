package by.ustsinovich.distcomp.lab1.model;

import java.time.LocalDateTime;
import java.util.List;

public class Issue extends AbstractEntity {

    private Long editorId;

    private String title;

    private String content;

    private LocalDateTime created;

    private LocalDateTime modified;

    private List<Long> marksIds;

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public List<Long> getMarksIds() {
        return marksIds;
    }

    public void setMarksIds(List<Long> marksIds) {
        this.marksIds = marksIds;
    }

}
