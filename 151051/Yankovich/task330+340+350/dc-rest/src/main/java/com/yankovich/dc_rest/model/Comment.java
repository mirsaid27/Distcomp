package com.yankovich.dc_rest.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "tbl_comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 2048)
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "tweetId", nullable = false)
    private Tweet tweet;

    private String country = "Belarus";
}
