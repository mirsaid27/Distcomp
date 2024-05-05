package by.bsuir.news.event;

import by.bsuir.news.dto.request.NoteRequestTo;

public record InTopicMessage (Operation operation, NoteRequestTo request) {
    public enum Operation {
        CREATE, UPDATE, DELETE, GET_ALL, GET_BY_ID
    }

    public InTopicMessage(Operation operation) {
        this(operation, null);
    }
}
