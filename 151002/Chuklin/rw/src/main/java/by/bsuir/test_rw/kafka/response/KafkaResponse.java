package by.bsuir.test_rw.kafka.response;

import by.bsuir.test_rw.kafka.RequestType;
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
