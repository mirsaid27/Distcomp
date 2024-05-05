package com.example.publicator.controller;

import com.example.publicator.KafkaSender;
import com.example.publicator.dto.NoteRequestTo;
import com.example.publicator.dto.NoteResponseTo;
import com.example.publicator.exception.NotFoundException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class NoteController {

    @Autowired
    private KafkaConsumer<String, NoteResponseTo> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";

    WebClient client = WebClient.create();
    private String uriBase = "http://localhost:24130/api/v1.0/";

    private NoteResponseTo listenKafka() throws NotFoundException{
        ConsumerRecords<String, NoteResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(500000));
        for(ConsumerRecord<String, NoteResponseTo> record: records){
            String key = record.key();
            NoteResponseTo value = record.value();
            if(value == null){
                throw new NotFoundException("Note not found", 404);
            }
            System.out.println(value.toString());
            return value;
        }
        return null;
    }



    @PostMapping(value = "notes")
    public ResponseEntity<?> create(@RequestHeader(value = "Accept-Language", defaultValue = "EN") String acceptLanguageHeader, @RequestBody NoteRequestTo noteRequestTo) {
        noteRequestTo.setCountry(acceptLanguageHeader);
        noteRequestTo.setMethod("POST");
        kafkaSender.sendMessage(noteRequestTo,inTopic);
        System.out.println(noteRequestTo.toString());

        // NoteResponseTo note = client.post().uri(uriBase + "notes").header(HttpHeaders.ACCEPT_LANGUAGE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(noteRequestTo), NoteRequestTo.class).retrieve().bodyToMono(NoteResponseTo.class).onErrorMap(NotFoundException.class, ex -> new NotFoundException(ex.getMessage(), ex.getStatus())).block();
        return new ResponseEntity<>(listenKafka(), HttpStatus.CREATED);
    }

    @GetMapping(value = "notes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<NoteResponseTo> list = client.get().uri(uriBase + "notes").retrieve().bodyToFlux(NoteResponseTo.class).collectList().block();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "notes/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        NoteRequestTo noteRequestTo = new NoteRequestTo();
        noteRequestTo.setMethod("GET");
        noteRequestTo.setId(id);
        kafkaSender.sendMessage(noteRequestTo,inTopic);
        System.out.println(noteRequestTo.toString());

        /*NoteResponseTo note = client.get().uri(uriBase + "notes/" + id).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                {
                    throw new NotFoundException("Note not found", 404);
                }
                )
                .bodyToMono(NoteResponseTo.class).block();
        */
        return new ResponseEntity<>(listenKafka(), HttpStatus.OK);
    }

    @PutMapping(value = "notes")
    public ResponseEntity<?> update(@RequestHeader(value = "Accept-Language", defaultValue = "EN") String acceptLanguageHeader, @RequestBody NoteRequestTo noteRequestTo) {
        noteRequestTo.setCountry(acceptLanguageHeader);
        noteRequestTo.setMethod("PUT");
        kafkaSender.sendMessage(noteRequestTo,inTopic);
        System.out.println(noteRequestTo.toString());
        /*NoteResponseTo note = client.put().uri(uriBase + "notes").header(HttpHeaders.ACCEPT_LANGUAGE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(noteRequestTo), NoteRequestTo.class).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        {
                            throw new NotFoundException("Note not found", 404);
                        }
                )
                .bodyToMono(NoteResponseTo.class).block();
        */
        return new ResponseEntity<>(listenKafka(), HttpStatus.OK);
    }

    @DeleteMapping(value = "notes/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        NoteRequestTo noteRequestTo = new NoteRequestTo();
        noteRequestTo.setMethod("DELETE");
        noteRequestTo.setId(id);
        kafkaSender.sendMessage(noteRequestTo,inTopic);
        System.out.println(noteRequestTo.toString());

        /*boolean isDeleted = Boolean.TRUE.equals(client.delete().uri(uriBase + "notes/" + id).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        {
                            throw new NotFoundException("Note not found", 404);
                        }
                )
                .bodyToMono(Boolean.class).block());
        */
        return new ResponseEntity<>(listenKafka(), HttpStatus.NO_CONTENT);
    }
}
