package by.bsuir.controllers;

import by.bsuir.dto.NoteRequestTo;
import by.bsuir.dto.NoteResponseTo;
import by.bsuir.exceptions.NotFoundException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
@CacheConfig(cacheNames = "noteCache")
public class NoteController {
    @Autowired
    private RestClient restClient;
    @Autowired
    private KafkaConsumer<String, NoteResponseTo> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;
    @Autowired
    private CacheManager cacheManager;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";
    private String uriBase = "http://localhost:24130/api/v1.0/notes";

    @GetMapping
    public ResponseEntity<List<?>> getNotes() {
        //kafkaSender.sendCustomNote();
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTo> getNote(@PathVariable Long id) throws NotFoundException {
        Cache cache = cacheManager.getCache("notes");
        if (cache != null) {
            NoteResponseTo cachedResponse = cache.get(id, NoteResponseTo.class);
            if (cachedResponse != null) {
                return ResponseEntity.status(200).body(cachedResponse);
            }
        }
        NoteRequestTo noteRequestTo = new NoteRequestTo();
        noteRequestTo.setMethod("GET");
        noteRequestTo.setId(id);
        kafkaSender.sendCustomNote(noteRequestTo, inTopic);
        NoteResponseTo response = listenKafka();

        if (response != null) {
            if (cache != null) {
                cache.put(id, response);
            }
            return ResponseEntity.status(200).body(response);
        } else {
            throw new NotFoundException("Note not found", 404L);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) throws NotFoundException {
        NoteRequestTo noteRequestTo = new NoteRequestTo();
        noteRequestTo.setMethod("DELETE");
        noteRequestTo.setId(id);
        kafkaSender.sendCustomNote(noteRequestTo, inTopic);
        listenKafka();
        Cache cache = cacheManager.getCache("notes");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping
    public ResponseEntity<NoteResponseTo> saveNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo note) throws NotFoundException {
        note.setCountry(acceptLanguageHeader);
        note.setMethod("POST");
        kafkaSender.sendCustomNote(note, inTopic);
        return ResponseEntity.status(201).body(listenKafka());
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTo> updateNote(@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguageHeader, @RequestBody NoteRequestTo note) throws NotFoundException {
        Cache cache = cacheManager.getCache("notes");
        if (cache != null) {
            cache.evict(note.getId());
        }
        note.setCountry(acceptLanguageHeader);
        note.setMethod("PUT");
        kafkaSender.sendCustomNote(note, inTopic);
        return ResponseEntity.status(200).body(listenKafka());
    }

    @GetMapping("/byTweet/{id}")
    public ResponseEntity<?> getUserByTweetId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byTweet/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }

    private NoteResponseTo listenKafka() throws NotFoundException {
        ConsumerRecords<String, NoteResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, NoteResponseTo> record : records) {

            String key = record.key();
            NoteResponseTo value = record.value();
            if (value == null) {
                throw new NotFoundException("Not found", 40400L);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("Received note: key = " + key + ", value = " + value +
                    ", offset = " + offset + ", partition = " + partition);

            return value;
        }
        return null;
    }
}
