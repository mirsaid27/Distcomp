package com.example.discussion.config.kafka;

import com.example.discussion.config.JsonMapper;
import com.example.discussion.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaExtractor {
    private final JsonMapper jsonMapper;

    public KafkaBatch extract(String data, Class<?> clazz) {
        KafkaBatch kafkaBatch = jsonMapper.toObject(data, KafkaBatch.class);
        Object object = null;
        if (kafkaBatch.getData() instanceof LinkedHashMap<?,?> map) {
            String content = jsonMapper.toString(kafkaBatch.getData());
            if (map.containsKey("code")) { // errormessage
                object = jsonMapper.toObject(content, ErrorMessage.class);
            } else {
                object = jsonMapper.toObject(content, clazz);
            }
        } else if (kafkaBatch.getData() instanceof String content) {
            object = jsonMapper.toObject(content, clazz);
        }
        kafkaBatch.setData(object);
        return kafkaBatch;
    }

    public KafkaBatch extractList(String data, Class<?> clazz) {
        KafkaBatch kafkaBatch = jsonMapper.toObject(data, KafkaBatch.class);
        Object object = null;
        if (kafkaBatch.getData() instanceof List<?> list) {
            String content = jsonMapper.toString(kafkaBatch.getData());
            object = jsonMapper.toObjectList(content, clazz);
        } else if (kafkaBatch.getData() instanceof LinkedHashMap<?,?> map) {
            String content = jsonMapper.toString(kafkaBatch.getData());
            object = jsonMapper.toObject(content, ErrorMessage.class);
        } else if (kafkaBatch.getData() instanceof String content) {
            object = jsonMapper.toObject(content, clazz);
        }
        kafkaBatch.setData(object);
        return kafkaBatch;
    }

}
