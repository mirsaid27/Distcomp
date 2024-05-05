package by.bsuir.discussion.kafka.request;

import by.bsuir.discussion.kafka.RequestType;
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
