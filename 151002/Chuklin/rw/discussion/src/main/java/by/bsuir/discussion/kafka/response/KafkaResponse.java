package by.bsuir.discussion.kafka.response;

import by.bsuir.discussion.kafka.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaResponse implements Serializable {
    private RequestType type;
    private String responseObj;
    private int status;
}
