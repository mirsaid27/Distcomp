package ru.bsuir.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkerResponseTo implements Serializable{

    private Long id;
    private String name;

}
