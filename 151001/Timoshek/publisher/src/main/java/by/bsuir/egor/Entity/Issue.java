package by.bsuir.egor.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tbl_issue", schema = "public")
public class Issue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_issue_id_seq")
    @SequenceGenerator(name = "tbl_issue_id_seq", sequenceName = "tbl_issue_id_seq", allocationSize = 1)
    private long id;
    @Column(nullable = false,name="title")
    private String title;
    @Column(nullable = false,name="content")
    private String content;
    @Column(nullable = false,name="created")
    private LocalDateTime created;
    @Column(nullable = false,name="modified")
    private LocalDateTime modified;

    @ManyToOne
    @JoinColumn(nullable = false, name = "editorId")
    private Editor editor;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            schema = "public",
            name = "tbl_issue_sticker",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "sticker_id")
    )
    private Set<Sticker> stickers;

    public String getTitle() {
        return title;
    }


    public void setEditor(Editor editor)
    {
        this.editor = editor;
    }
    public Editor getEditor()
    {
        return this.editor;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public String getContent() {
        return content;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
