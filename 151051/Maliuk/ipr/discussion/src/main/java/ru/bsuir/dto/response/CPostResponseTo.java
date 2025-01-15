package ru.bsuir.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPostResponseTo implements Serializable {

    private Long id;
    private Long storyId;

    private String content;
    private String country = "BY";

    private boolean errorExist;
}