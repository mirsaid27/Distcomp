package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.bsuir.abstractions.AbstractEntity;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_story")
public class Story extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @Column(unique = true)
    @Size(min = 2, max = 64)
    private String title;

    @Column(length = 2048)
    @Size(min = 4, max = 2048)
    private String content;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime modified;
}
