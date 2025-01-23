package ru.bsuir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column( nullable = false)
    @Size(min = 4, max = 2048)
    private String content;

    @ManyToOne
    @JoinColumn(name = "tweetId", nullable = false)
    private Tweet tweet;

    private String country = "Belarus";
}

