package by.bsuir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_note", schema = "distcomp")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne (cascade=CascadeType.REFRESH)
    @JoinColumn (name="tweet_id")
    private Tweet tweet;
    @Column(name = "content")
    private String content;
}
