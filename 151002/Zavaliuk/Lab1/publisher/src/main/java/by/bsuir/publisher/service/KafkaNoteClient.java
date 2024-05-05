package by.bsuir.publisher.service;

import by.bsuir.publisher.event.*;
import by.bsuir.publisher.service.exceptions.NoteExchangeFailedException;
import by.bsuir.publisher.service.exceptions.NoteExchangeTimeoutException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class KafkaNoteClient {
    public static final String IN_TOPIC = "in-topic";
    public static final String OUT_TOPIC = "out-topic";

    private final KafkaTemplate<String, InTopicEvent> kafkaTemplate;
    private final ConcurrentHashMap<UUID, Exchanger<Exchangeable>> kafkaExchangers =
            new ConcurrentHashMap<>();

    private static long hashMessage(InTopicMessage message) {
        if (message == null)
            return 0;
        final NoteInTopicTo dto = message.noteDto();
        if (dto == null)
            return 0;
        final Long newsId = dto.getNewsId();
        if (newsId == null)
            return 0;
        return newsId;
    }

    public OutTopicMessage sync(InTopicMessage message) {
        final UUID id = UUID.randomUUID();
        final InTopicEvent inTopicEvent = new InTopicEvent(id, message);
        Exchanger<Exchangeable> exchanger = new Exchanger<>();
        kafkaExchangers.put(id, exchanger);
        try {
            kafkaTemplate.send(IN_TOPIC, Long.toString(hashMessage(message)), inTopicEvent);
            Exchangeable result = exchanger.exchange(inTopicEvent, 1, TimeUnit.SECONDS);
            if (result instanceof OutTopicEvent outEvent) {
                return outEvent.message();
            } else {
                throw new NoteExchangeFailedException("Invalid note response data");
            }
        } catch (TimeoutException ex) {
            kafkaExchangers.remove(id);
            throw new NoteExchangeTimeoutException("Response time has expired", ex);
        } catch (InterruptedException ex) {
            throw new NoteExchangeFailedException("Note data request failed", ex);
        }
    }

    @KafkaListener(topics = OUT_TOPIC, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void listen(ConsumerRecord<String, OutTopicEvent> record) {
        final OutTopicEvent event = record.value();
        final UUID key = event.id();
        Exchanger<Exchangeable> exchanger = kafkaExchangers.remove(key);
        if (exchanger != null) {
            try {
                exchanger.exchange(event);
            } catch (InterruptedException ex) {
                throw new NoteExchangeFailedException("Note data request failed", ex);
            }
        }
    }
}