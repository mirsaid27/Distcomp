package by.molchan.Distcomp.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tbl_label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany()
    @JoinTable(name = "tbl_article_label", joinColumns = {@JoinColumn (name = "labelId")}, inverseJoinColumns = {@JoinColumn (name = "articleId")})
    private List<Article> articles = new ArrayList<>();

    public long getId() {
        return id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id && name.equals(label.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Label(String name) {
        this.name = name;
    }

    public Label() {
    }
}
