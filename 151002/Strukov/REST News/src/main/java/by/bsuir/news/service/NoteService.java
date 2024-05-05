package by.bsuir.news.service;

import by.bsuir.news.config.RestClientConfig;
import by.bsuir.news.dto.request.NoteRequestTo;
import by.bsuir.news.dto.response.NoteResponseTo;
import by.bsuir.news.entity.News;
import by.bsuir.news.entity.Note;
import by.bsuir.news.event.InTopicMessage;
import by.bsuir.news.event.OutTopicMessage;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.NewsRepository;
import by.bsuir.news.repository.NoteRepository;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@CacheConfig(cacheNames = "notesCache")
@RequiredArgsConstructor
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NewsRepository newsRepository;
    private static final SecureRandom random;
    private final KafkaClient kafkaClient;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }

    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    @CacheEvict(cacheNames = "notes", allEntries = true)
    public NoteResponseTo create(@Valid NoteRequestTo request) throws ClientException {
        Long newsId = request.getNewsId();
        if(!newsRepository.existsById(newsId)) {
            throw new ClientException("news with id=" + newsId + " doesn't exist");
        }
        request.setId(getTimeBasedId());
        InTopicMessage inMessage = new InTopicMessage(InTopicMessage.Operation.CREATE, request);
        OutTopicMessage outMessage = kafkaClient.sync(inMessage);
        return outMessage.resultList().stream().findFirst().orElseThrow(() -> new ClientException("Could not create new note"));
    }

    @Cacheable(cacheNames = "notes")
    public List<NoteResponseTo> getAll() throws ClientException {
        InTopicMessage inMessage = new InTopicMessage(InTopicMessage.Operation.GET_ALL);
        OutTopicMessage outMessage = kafkaClient.sync(inMessage);
        return outMessage.resultList();
    }

    @Cacheable(cacheNames = "notes", key = "#id", unless = "#result == null")
    public NoteResponseTo getById(Long id) throws ClientException{
        NoteRequestTo request = new NoteRequestTo();
        request.setId(id);
        InTopicMessage inMessage = new InTopicMessage(InTopicMessage.Operation.GET_BY_ID, request);
        OutTopicMessage outMessage = kafkaClient.sync(inMessage);
        return outMessage.resultList().stream().findFirst().orElseThrow(() -> new ClientException("Could not get note by id"));
    }

    @CacheEvict(cacheNames = "notes", allEntries = true)
    public NoteResponseTo update(@Valid NoteRequestTo request) throws ClientException {
        Long newsId = request.getNewsId();
        if(!newsRepository.existsById(newsId)) {
            throw new ClientException("news with id=" + newsId + " doesn't exist");
        }
        InTopicMessage inMessage = new InTopicMessage(InTopicMessage.Operation.UPDATE, request);
        OutTopicMessage outMessage = kafkaClient.sync(inMessage);
        return outMessage.resultList().stream().findFirst().orElseThrow(() -> new ClientException("Could not update note"));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "notes", key = "#id"),
            @CacheEvict(cacheNames = "notes", allEntries = true) })
    public Long delete(Long id) throws ClientException {
        NoteRequestTo request = new NoteRequestTo();
        request.setId(id);
        InTopicMessage inMessage = new InTopicMessage(InTopicMessage.Operation.DELETE, request);
        OutTopicMessage outMessage = kafkaClient.sync(inMessage);
        Long deletedId = outMessage.resultList().stream().findFirst().orElseThrow(()->new ClientException("Could not delete note")).getId();
        if(!deletedId.equals(id)) {
            throw new ClientException("Deleted wrong note");
        }
        return deletedId;
    }
}
