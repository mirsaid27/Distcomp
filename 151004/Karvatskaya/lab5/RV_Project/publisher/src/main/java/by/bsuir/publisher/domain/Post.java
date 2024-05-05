package by.bsuir.publisher.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@RedisHash("post")
public class Post implements Serializable {
    @Id
    private Long id;
    private Long issueId;
    private String content;
}
