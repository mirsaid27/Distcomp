package com.example.discussion.config;

import java.util.Optional;

public class LongUtils {
    public static Long valueOf(Object str) {
        return Optional.ofNullable(str)
                .map(Object::toString)
                .map(Long::valueOf)
                .orElse(null);
    }
}
