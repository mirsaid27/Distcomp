package by.bsuir.news.event;

import by.bsuir.news.dto.response.NoteResponseTo;

import java.util.List;

public record OutTopicMessage(List<NoteResponseTo> resultList) {
}
