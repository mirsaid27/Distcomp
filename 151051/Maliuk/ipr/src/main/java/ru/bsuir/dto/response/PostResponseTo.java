package ru.bsuir.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseTo implements Serializable {

    Long id;
    Long storyId;
    String content;
    private String country = "Belarus";

}