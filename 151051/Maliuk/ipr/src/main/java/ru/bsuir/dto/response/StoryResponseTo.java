package ru.bsuir.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponseTo implements Serializable{

    private Long id;

    private Long creatorId;

    private String title;

    private String content;

}
