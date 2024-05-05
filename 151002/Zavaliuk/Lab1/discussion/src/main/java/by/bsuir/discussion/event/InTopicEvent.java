package by.bsuir.discussion.event;

import java.util.UUID;

public record InTopicEvent(
        UUID id,
        InTopicMessage message
)  implements Exchangeable {
}