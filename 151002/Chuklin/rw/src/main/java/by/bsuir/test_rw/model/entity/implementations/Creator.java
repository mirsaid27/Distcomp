package by.bsuir.test_rw.model.entity.implementations;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_creator")
public class Creator implements EntityModel<Long>, Serializable {
    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    @OneToMany
    private List<Issue> issues;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
