package by.bsuir.news.event;

import java.util.UUID;

public record OutTopicEvent (UUID id, OutTopicMessage message) implements Exchangeable {
}
