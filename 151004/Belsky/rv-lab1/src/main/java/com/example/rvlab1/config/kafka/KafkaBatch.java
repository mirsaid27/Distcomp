package com.example.rvlab1.config.kafka;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Data
@Accessors(chain = true)
@Slf4j
public class KafkaBatch {
    private String type;
    private Map<String, Object> params;
    private Object data;
}
