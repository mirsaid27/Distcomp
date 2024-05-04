package by.bsuir.poit.dc.cassandra.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * @author Name Surname
 * 
 */
@Builder
public record ErrorDto(
    String errorCode,
    String errorMessage,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable String[] errors
) {
    public static final int MAGIC_CODE = 66;

    public static String codeOf(HttpStatus status, int code) {
	return STR."\{status.value()}\{code}";
    }
}
