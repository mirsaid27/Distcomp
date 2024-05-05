package by.bsuir.services;

import by.bsuir.controllers.KafkaSender;
import by.bsuir.dto.NoteRequestTo2;
import by.bsuir.dto.NoteResponseTo2;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.repository.StoryRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@CacheConfig(cacheNames = "notesCache")
public class NoteService {

    @Autowired
    private StoryRepository storyDao;
    @Autowired
    private KafkaConsumer<String, NoteResponseTo2> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;

    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";

    @Cacheable(cacheNames = "notes", key = "#id", unless = "#result == null")
    public NoteResponseTo2 getNote(Long id) {
        NoteRequestTo2 noteRequestTo2 = new NoteRequestTo2();
        noteRequestTo2.setMethod("GET");
        noteRequestTo2.setId(id);
        kafkaSender.sendCustomMessage(noteRequestTo2, inTopic);
        return listenKafka();
    }

    @Caching(evict = { @CacheEvict(cacheNames = "notes", key = "#id"),
            @CacheEvict(cacheNames = "notes", allEntries = true) })
    public NoteResponseTo2 deleteNote(Long id){
        NoteRequestTo2 noteRequestTo2 = new NoteRequestTo2();
        noteRequestTo2.setMethod("DELETE");
        noteRequestTo2.setId(id);
        kafkaSender.sendCustomMessage(noteRequestTo2, inTopic);
        return listenKafka();
    }

    @CacheEvict(cacheNames = "notes", allEntries = true)
    public NoteResponseTo2 updateNote(String acceptLanguageHeader, NoteRequestTo2 note){
        note.setCountry(acceptLanguageHeader);
        note.setMethod("PUT");
        kafkaSender.sendCustomMessage(note, inTopic);
        return listenKafka();
    }

    @CacheEvict(cacheNames = "storys", allEntries = true)
    public NoteResponseTo2 saveNote(String acceptLanguageHeader, NoteRequestTo2 note){
        note.setCountry(acceptLanguageHeader);
        note.setMethod("POST");
        long storyId = note.getStoryId();
        boolean existsById = storyDao.existsById(storyId);
        if (!existsById) {
            throw new NotFoundException("No story with such ID", 40400L);
        }
        kafkaSender.sendCustomMessage(note, inTopic);
        return listenKafka();
    }

    private NoteResponseTo2 listenKafka() throws NotFoundException {
        ConsumerRecords<String, NoteResponseTo2> records = kafkaConsumer.poll(Duration.ofMillis(50000));
        for (ConsumerRecord<String, NoteResponseTo2> record : records) {

            String key = record.key();
            NoteResponseTo2 value = record.value();
            if (value == null) {
                throw new NotFoundException("Not found", 40400L);
            }
            long offset = record.offset();
            int partition = record.partition();
            System.out.println("Received message: key = " + key + ", value = " + value +
                    ", offset = " + offset + ", partition = " + partition);

            return value;
        }
        return null;
    }

}
