package com.example.rvlab1.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "tblIssue")
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "title", length = 64, nullable = false, unique = true)
    private String title;

    @Column(name = "content", length = 2048)
    private String content;

    @Column(name = "created")
    @CreationTimestamp
    private OffsetDateTime created;

    @Column(name = "modified")
    @UpdateTimestamp
    private OffsetDateTime modified;
}
