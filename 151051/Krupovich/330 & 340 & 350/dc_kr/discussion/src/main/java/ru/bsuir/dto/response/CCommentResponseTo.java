package ru.bsuir.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CCommentResponseTo implements Serializable {

    private Long id;
    private Long tweetId;

    private String content;
    private String country = "BY";

    private boolean errorExist;
}