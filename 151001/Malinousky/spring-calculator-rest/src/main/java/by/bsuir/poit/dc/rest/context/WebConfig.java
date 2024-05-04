package by.bsuir.poit.dc.rest.context;

import by.bsuir.poit.dc.rest.api.dto.response.StickerDto;
import by.bsuir.poit.dc.rest.api.dto.response.TweetDto;
import by.bsuir.poit.dc.rest.api.dto.response.CommentDto;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * @author Name Surname
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplate cassandraTemplate() {
	return new RestTemplateBuilder().
		   rootUri("localhost:24130/api/v1.0")
		   .build();
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
	return builder -> builder
			      .withCacheConfiguration("commentCache", forClass(CommentDto.class, 12))
			      .withCacheConfiguration("tweetsCache", forClass(TweetDto.class, 30))
			      .withCacheConfiguration("stickerCache", forClass(StickerDto.class, 50));
    }

    private <T> RedisCacheConfiguration forClass(Class<T> clazz, int timeout) {
	return RedisCacheConfiguration.defaultCacheConfig()
		   .entryTtl(Duration.ofSeconds(timeout))
		   .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(ProtobufRedisSerializer.with(clazz)))
		   .disableCachingNullValues();

    }
}
