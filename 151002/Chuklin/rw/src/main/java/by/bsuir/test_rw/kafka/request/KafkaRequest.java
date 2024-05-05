package by.bsuir.test_rw.kafka.request;

import by.bsuir.test_rw.kafka.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaRequest implements Serializable {
    private RequestType type;
    private List<String> args;
}
