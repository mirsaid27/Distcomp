package by.bsuir.publisher.util;

import org.springframework.http.HttpHeaders;

public class HeadersUtil {
    private HeadersUtil() {}

    public static void AddHeadersWithoutLength(HttpHeaders httpHeaders, HttpHeaders added) {
        httpHeaders.addAll(added);
        httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
    }
}