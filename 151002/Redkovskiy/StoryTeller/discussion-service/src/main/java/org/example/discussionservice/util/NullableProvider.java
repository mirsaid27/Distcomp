package org.example.discussionservice.util;

import org.example.discussionservice.util.exception.DataNotFoundException;

public class NullableProvider {

    public static <T> T requireNotNull(T object) {
        if (object == null) {
            throw new DataNotFoundException("Data not found");
        }
        return object;
    }
}
