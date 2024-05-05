package by.bsuir.news.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_news")
@Getter
@Setter
public class News implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Size(min = 2, max = 64)
    private String title;
    @Size(min = 4, max = 2048)
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    //private Long editorId;
    @ManyToOne
    @JoinColumn(name = "editorId")
    private Editor editorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "news")
    private List<NewsMarker> newsMarkers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "news")
    private List<Note> notes;

//    @ManyToMany
//    private List<Marker> markers;

    public News(){

    }

    public Editor getEditorId() {
        return editorId;
    }

    public void setEditorId(Editor editorId) {
        this.editorId = editorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public List<NewsMarker> getNewsMarkers() {
        return newsMarkers;
    }

    public void setNewsMarkers(List<NewsMarker> newsMarkers) {
        this.newsMarkers = newsMarkers;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

//    public Editor getEdit() {
//        return edit;
//    }
//
//    public void setEdit(Editor edit) {
//        this.edit = edit;
//    }
}
