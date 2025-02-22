package by.kopvzakone.distcomp.entities;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reaction {
    long id;
    long tweetId;
    @Size(min = 2, max = 32)
    String content;
}
