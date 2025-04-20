package com.dc.service;

import com.dc.exception.ServiceException;
import com.dc.mapper.MessageMapper;
import com.dc.model.blo.Message;
import com.dc.model.dto.MessageRequestTo;
import com.dc.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MessageService {

    private WebClient webClient = WebClient.create("http://localhost:24130/api/v1.0/messages");

    @Autowired
    private MessageMapper mapper;

    @Autowired
    private NewsRepository newsService;

    public Collection<MessageRequestTo> getAll() {
        Collection<MessageRequestTo> collection = webClient.get().retrieve().bodyToFlux(MessageRequestTo.class).collectList().block();
        return collection;
    }

    public Message getById(Long id) {
        MessageRequestTo collection = webClient.get().uri("/" + id).retrieve().bodyToMono(MessageRequestTo.class).block();

        Message buf = mapper.mapToBlo(collection);
        buf.setNews(newsService.findById(collection.getNewsId()).orElseThrow());
        return buf;
    }

    public Message save(MessageRequestTo Message) {
        MessageRequestTo m = webClient.post().uri("").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Message).exchangeToMono(response -> {
                    HttpStatusCode status = response.statusCode();

                    if (status == HttpStatus.CREATED) {
                        return response.bodyToMono(MessageRequestTo.class);
                    } else {
                        return Mono.error(new ServiceException("Ошибка", 404));
                    }
                }).block();
        return mapper.mapToBlo(m);
    }

    public Message update(MessageRequestTo Message) {
        MessageRequestTo m = webClient.put().uri("").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Message).retrieve()
                .bodyToMono(MessageRequestTo.class).block();
        return mapper.mapToBlo(m);
    }

    public void delete(Long id) {
        webClient.delete().uri("/" + id)
                .exchangeToMono(response -> {
                    if (response.statusCode() != HttpStatus.NO_CONTENT)
                        throw new ServiceException("Нечего удалить", 404);
                    return Mono.empty();
                }).block();
    }

    private boolean validator(Message Message) {
        if (Message.getContent().length() < 2 || Message.getContent().length() > 2048
        ) {
            return false;
        }
        return true;
    }
}
