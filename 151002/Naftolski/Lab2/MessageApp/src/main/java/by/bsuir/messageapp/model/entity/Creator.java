package by.bsuir.messageapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_creator")
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", unique = true, nullable = false, length = 64)
    private String login;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "firstname", length = 64)
    private String firstName;

    @Column(name = "lastname", length = 64)
    private String lastName;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Story> stories = new ArrayList<>();
}
