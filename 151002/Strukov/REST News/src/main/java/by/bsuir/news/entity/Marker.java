package by.bsuir.news.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tbl_marker")
public class Marker implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Size(min = 2, max = 32)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marker")
    private List<NewsMarker> newsMarkers;

    public Marker() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewsMarker> getNewsMarkers() {
        return newsMarkers;
    }

    public void setNewsMarkers(List<NewsMarker> newsMarkers) {
        this.newsMarkers = newsMarkers;
    }
}
