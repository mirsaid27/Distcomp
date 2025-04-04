package org.example.discussion.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

//@Data
//@Table(name = "tbl_message")
//public class Message {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    @ManyToOne
//    @JoinColumn(name = "story_id", nullable = false)
//    private Story story;
//    private String content;
//}
import java.util.UUID;

@Table("tbl_message")
@Data
public class Message {

    @PrimaryKey
    private UUID id;

    private String country;
    private Long storyId;
    private String content;
}