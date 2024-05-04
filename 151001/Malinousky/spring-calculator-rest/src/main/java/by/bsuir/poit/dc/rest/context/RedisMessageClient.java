package by.bsuir.poit.dc.rest.context;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

/**
 * @author Name Surname
 * 
 */
@RequiredArgsConstructor
public class RedisMessageClient {
    private final ReactiveRedisTemplate<String, UpdateCommentDto> redisTemplate;
    private final String redisKey = "publisher:message:response";
}
