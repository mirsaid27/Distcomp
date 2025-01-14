package by.bsuir.dc.impl.author.model;

public record AuthorResponse(
        long id,
        String login,
        String password,
        String firstname,
        String lastname
){}
