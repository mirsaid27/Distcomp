package by.bsuir.egor.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_comment", schema = "public")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_comment_id_seq")
    @SequenceGenerator(name = "tbl_comment_id_seq", sequenceName = "tbl_comment_id_seq", allocationSize = 1)
    private long id;
    @Column(nullable = false,name="content")
    private String content;


    @Column(name = "issueId",nullable = false)
    private Long issueId;
    public Comment() {

    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
