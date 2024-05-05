package by.bsuir.test_rw.model.entity.implementations;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "tbl_note")
public class Note implements EntityModel<Long>, Serializable {

    @GeneratedValue
    @Id
    private Long id;
    private String content;
    @ManyToOne
    private Issue issue;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }
}
