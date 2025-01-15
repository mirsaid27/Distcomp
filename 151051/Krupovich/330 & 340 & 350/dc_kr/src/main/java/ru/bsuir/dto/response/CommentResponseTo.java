package ru.bsuir.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseTo implements Serializable {

    Long id;
    Long tweetId;
    String content;
    private String country = "Belarus";

}