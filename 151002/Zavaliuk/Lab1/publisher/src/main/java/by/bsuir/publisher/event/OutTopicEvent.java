package by.bsuir.publisher.event;

import java.util.UUID;

public record OutTopicEvent(
        UUID id,
        OutTopicMessage message
) implements Exchangeable {
}