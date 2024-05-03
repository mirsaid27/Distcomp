package com.example.discussion.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ErrorMessage {
    private String errorMessage;
    private String errorCode;
    private HttpStatus httpStatus;

    public ErrorMessage(ServiceErrorCode errorCode) {
        this.errorMessage = errorInfoMap.get(errorCode).getErrorMessage();
        this.errorCode = errorInfoMap.get(errorCode).getErrorCode();
        this.httpStatus = errorInfoMap.get(errorCode).getHttpStatus();
    }

    private static final Map<ServiceErrorCode, ErrorMessage> errorInfoMap;
    static {
        Yaml yaml = new Yaml();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("./errormessage.yaml");
        } catch (FileNotFoundException e) {
            log.error("Не удалось загрузить errormessage.yaml");
            throw new RuntimeException("Не удалось загрузить errormessage.yaml", e);
        }
        Map<String, Map<String, Object>> errorMessageMap = yaml.load(inputStream);
        errorInfoMap = new HashMap<>();
        errorMessageMap.forEach((key, value) -> errorInfoMap.put(Enum.valueOf(ServiceErrorCode.class, key), new ErrorMessage()
                .setErrorMessage(String.valueOf(value.get("error_message")))
                .setErrorCode(String.valueOf(value.get("error_code")))
                .setHttpStatus(HttpStatus.valueOf((int) value.get("http_status")))
        ));
    }
}
