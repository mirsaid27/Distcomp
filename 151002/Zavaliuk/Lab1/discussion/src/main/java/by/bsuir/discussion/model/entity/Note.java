package by.bsuir.discussion.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("tbl_note")
public class Note {
    @PrimaryKey
    private NoteKey key;

    @Column("content")
    private String content;
}