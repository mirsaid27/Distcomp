package by.bsuir.discussion.model.entity.implementations;

import by.bsuir.discussion.model.entity.interfaces.EntityModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Setter
@Getter
@Table("tbl_note")
public class Note implements EntityModel<Long> {

    @PrimaryKey
    private Long id;
    private String content;
    private Long issueId;
    @Transient
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
