package by.bsuir.publisher.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_sticker")
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;

    @ManyToMany
    @JoinTable(name = "tbl_newssticker",
            joinColumns = @JoinColumn(name = "sticker_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id"))
    private List<News> news = new ArrayList<>();

}