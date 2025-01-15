package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.bsuir.abstractions.AbstractEntity;


@Entity
@Data
@Table(name = "tbl_comment")
public class Comment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet;

    @Column(length = 2048, nullable = false)
    @Size(min = 4, max = 2048)
    private String content;
}

