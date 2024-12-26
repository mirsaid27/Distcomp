package org.example.tweetapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public class Tweet extends BaseEntity {
    @NotNull
    private Long authorId;

    @NotNull
    @Size(max = 64)
    private String title;

    @NotNull
    @Size(max = 2048)
    private String content;

    @NotNull
    @PastOrPresent
    private LocalDateTime created;

    @PastOrPresent
    private LocalDateTime modified;
}
