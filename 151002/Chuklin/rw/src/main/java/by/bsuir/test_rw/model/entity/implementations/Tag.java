package by.bsuir.test_rw.model.entity.implementations;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "tbl_tag")
public class Tag implements EntityModel<Long>, Serializable {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    @ManyToMany
    private List<Issue> issues;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }
}
