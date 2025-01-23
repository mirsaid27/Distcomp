package org.example.tweetapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 2048)
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet;
}
