package com.rmakovetskij.dc_rest.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "tbl_message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 2048)
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "issueId", nullable = false)
    private Issue issue;

    private String country = "Belarus";
}
