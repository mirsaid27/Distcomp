package com.example.discussion.config.kafka;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Accessors(chain = true)
public class KafkaBatch {
    private String type;
    private Map<String, Object> params;
    private Object data;
}
