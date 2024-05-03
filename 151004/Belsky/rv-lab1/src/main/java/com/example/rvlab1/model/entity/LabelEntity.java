package com.example.rvlab1.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "tblLabel")
public class LabelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 32)
    private String name;

    @JoinTable(name = "tblIssueLabel", joinColumns = @JoinColumn(name = "issueId"), inverseJoinColumns = @JoinColumn(name = "labelId"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<IssueEntity> issues = new HashSet<>();
}
