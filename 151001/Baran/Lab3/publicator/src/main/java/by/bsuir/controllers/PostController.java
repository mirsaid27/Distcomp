package by.bsuir.controllers;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
public class PostController {
    @Autowired
    private RestClient restClient;


    private String uriBase = "http://localhost:24130/api/v1.0/posts";

    @GetMapping
    public ResponseEntity<List<?>> getPosts(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase)
                .retrieve()
                .body(List.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseTo> getPost(@PathVariable Long id) {
        System.out.println("get:");
        return ResponseEntity.status(200).body(restClient.get()
                .uri(uriBase + "/" + id)
                .retrieve()
                .body(PostResponseTo.class));
        //ResponseEntity<PostResponseTo> mock = ResponseEntity.status(200).body(PutMock.getBody());
        //System.out.println(mock);
        //return mock;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        System.out.println("______________________________________________________________________________________________________");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(restClient.delete()
                .uri(uriBase + "/" + id)
                .retrieve()
                .toBodilessEntity().getBody());
    }

    private ResponseEntity<PostResponseTo> PutMock;
    @PostMapping
    public ResponseEntity<PostResponseTo> savePost(@RequestHeader HttpHeaders headers, @RequestBody PostRequestTo post) {
        System.out.println("POST");
        ResponseEntity entity = ResponseEntity.status(201).body(restClient.post()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(49)
                .body(post)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(PostResponseTo.class));
        PutMock = entity;
        return entity;
    }

    @PutMapping()
    public ResponseEntity<PostResponseTo> updatePost(@RequestHeader HttpHeaders headers,@RequestBody PostRequestTo post) {
        ResponseEntity<PostResponseTo> e = ResponseEntity.status(200).body(restClient.put()
                .uri(uriBase)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(62)
                .body(post)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(PostResponseTo.class));
        return e;
//        PostResponseTo responseBody = PutMock.getBody();
//        responseBody.content = post.getContent();
//        PutMock = ResponseEntity.status(200).body(responseBody);
//        System.out.println("put:");
//        System.out.println(PutMock);
//        return PutMock;
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<?> getEditorByIssueId(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        return restClient.get()
                .uri(uriBase + "/byIssue/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(ResponseEntity.class);
    }
}
