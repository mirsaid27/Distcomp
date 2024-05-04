package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Name Surname
 * 
 */
@Entity
@Table(name = "tweets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Size(min = 2, max = 64)
    @Column(name = "title",
	length = 64,
	nullable = false,
	unique = true)
    private String title;

    @Size(min = 4, max = 2048)
    @Column(name = "content", length = 2048, nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Column(name = "modified", nullable = false)
    private Timestamp modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id", referencedColumnName = "id")
    private Editor editor;

    @OneToMany(mappedBy = "tweet", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<TweetSticker> stickers = new ArrayList<>();

    public void addSticker(TweetSticker sticker) {
	stickers.add(sticker);
    }


    @PrePersist
    public void beforeInsert() {
	var timestamp = Timestamp.from(Instant.now());
	this.created = timestamp;
	this.modified = timestamp;
    }

    @PostUpdate
    public void beforeUpdate() {
        this.modified = Timestamp.from(Instant.now());
    }
}
