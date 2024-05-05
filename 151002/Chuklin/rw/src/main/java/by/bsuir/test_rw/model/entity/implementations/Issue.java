package by.bsuir.test_rw.model.entity.implementations;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_issue")
public class Issue implements EntityModel<Long>, Serializable {
    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    private String title;
    private String content;
    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date modified;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;
    @ManyToMany
    private List<Tag> tags;
    @OneToMany
    private List<Note> notes;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }
}
