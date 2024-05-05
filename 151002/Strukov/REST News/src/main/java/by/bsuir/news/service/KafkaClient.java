package by.bsuir.news.service;

import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.event.*;
import by.bsuir.news.exception.ClientException;
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
public class KafkaClient {
    public static final String inTopic = "inTopic";
    public static final String outTopic = "outTopic";

    private final KafkaTemplate<String, InTopicEvent> kafkaTemplate;
    private final ConcurrentHashMap<UUID, Exchanger<Exchangeable>> kafkaExchangers = new ConcurrentHashMap<>();

    private static long hashMessage(InTopicMessage message) {
        if (message == null)
            return 0;
        final NoteRequestTo dto = message.request();
        if (dto == null)
            return 0;
        final Long newsId = dto.getNewsId();
        if (newsId == null)
            return 0;
        return newsId;
    }

    public OutTopicMessage sync(InTopicMessage message) throws ClientException {
        final UUID id = UUID.randomUUID();
        final InTopicEvent inTopicEvent = new InTopicEvent(id, message);
        Exchanger<Exchangeable> exchanger = new Exchanger<>();
        kafkaExchangers.put(id, exchanger);
        try {
            kafkaTemplate.send(inTopic, Long.toString(hashMessage(message)), inTopicEvent);
            Exchangeable result = exchanger.exchange(inTopicEvent, 2, TimeUnit.SECONDS);
            if (result instanceof OutTopicEvent outEvent) {
                return outEvent.message();
            } else {
                throw new ClientException("Invalid comment response data");
            }
        } catch (TimeoutException ex) {
            kafkaExchangers.remove(id);
            throw new ClientException("Response time has expired");
        } catch (InterruptedException ex) {
            throw new ClientException("Comment data request failed");
        }
    }

    @KafkaListener(topics = outTopic, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void listen(ConsumerRecord<String, OutTopicEvent> record) throws ClientException {
        final OutTopicEvent event = record.value();
        final UUID key = event.id();
        Exchanger<Exchangeable> exchanger = kafkaExchangers.remove(key);
        if (exchanger != null) {
            try {
                exchanger.exchange(event);
            } catch (InterruptedException ex) {
                throw new ClientException("Comment data request failed");
            }
        }
    }

}
