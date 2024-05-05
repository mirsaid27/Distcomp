package by.bsuir.egor.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tbl_editor", schema = "public")
public class Editor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_editor_id_seq")
    @SequenceGenerator(name = "tbl_editor_id_seq", sequenceName = "tbl_editor_id_seq", allocationSize = 1)
    private long id;
    @Column(nullable = false,name="login")
    private String login;
    @Column(nullable = false,name="password")
    private String password;
    @Column(nullable = false,name="firstname")
    private String firstname;
    @Column(nullable = false,name="lastname")
    private String lastname;
    @OneToMany(mappedBy = "editor")
    private Set<Issue> issues;

    public Editor() {

    }

    public Set<Issue> getIssues()
    {
        return  issues;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                '}';
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
