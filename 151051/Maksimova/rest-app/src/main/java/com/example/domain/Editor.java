package com.example.domain;

public class Editor {
    private Long id;
    private String login;
    private String name;

    // Конструктор без параметров
    public Editor() {
    }

    // Конструктор с параметрами
    public Editor(Long id, String login, String name) {
        this.id = id;
        this.login = login;
        this.name = name;
    }

    // Геттер для id
    public Long getId() {
        return id;
    }

    // Сеттер для id
    public void setId(Long id) {
        this.id = id;
    }

    // Геттер для login
    public String getLogin() {
        return login;
    }

    // Сеттер для login
    public void setLogin(String login) {
        this.login = login;
    }

    // Геттер для name
    public String getName() {
        return name;
    }

    // Сеттер для name
    public void setName(String name) {
        this.name = name;
    }

    // Переопределение метода equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Editor editor = (Editor) o;
        return id.equals(editor.id) && login.equals(editor.login) && name.equals(editor.name);
    }

    // Переопределение метода hashCode
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Editor{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

