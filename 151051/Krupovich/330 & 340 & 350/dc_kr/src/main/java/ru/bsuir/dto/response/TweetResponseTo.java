package ru.bsuir.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponseTo implements Serializable{

    private Long id;

    private Long editorId;

    private String title;

    private String content;

}
