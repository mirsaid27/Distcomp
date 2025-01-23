package com.rmakovetskij.dc_rest.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
@Table(name = "tbl_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 2048)
    @Column(nullable = false)
    private String content;

    // Ассоциативное поле, связь с Issue
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
}
