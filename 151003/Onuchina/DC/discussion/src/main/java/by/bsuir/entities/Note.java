package by.bsuir.entities;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(value = "tbl_note")
public class Note {

    @PrimaryKey
    private NoteKey key = new NoteKey();

//    @PrimaryKeyColumn(name = "country", type = PARTITIONED)
//    private String country;
//
//    @PrimaryKeyColumn(name = "storyId", ordinal = 0, type = CLUSTERED)
//    private Long storyId;
//
//    @PrimaryKeyColumn(name = "id", ordinal = 1, type = CLUSTERED)
//    private Long id;

    @Column("content")
    private String content;

}
