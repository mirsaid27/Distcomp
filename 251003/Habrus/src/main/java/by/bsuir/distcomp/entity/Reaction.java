package by.bsuir.distcomp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reaction implements Identifiable {

    private Long id;
    private Long articleId;
    private String content;

}
