package by.bsuir.egor.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tbl_sticker", schema = "public")
public class Sticker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_sticker_id_seq")
    @SequenceGenerator(name = "tbl_sticker_id_seq", sequenceName = "tbl_sticker_id_seq", allocationSize = 1)
    private long id;
    @Column(nullable = false,name="name")
    private String name;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            schema = "public",
            name = "tbl_issue_sticker",
            joinColumns = @JoinColumn(name = "sticker_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    private Set<Issue> issues;

    public Sticker() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

