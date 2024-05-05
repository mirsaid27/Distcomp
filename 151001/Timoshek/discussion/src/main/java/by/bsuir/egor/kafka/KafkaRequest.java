package by.bsuir.egor.kafka;

import by.bsuir.egor.dto.CommentRequestTo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KafkaRequest {
    private UUID key;

    private KafkaMethods requestMethod;

    private List<CommentRequestTo> dtoToTransfer = new ArrayList<>();

    public KafkaMethods getRequestMethod() {
        return requestMethod;
    }

    public List<CommentRequestTo> getDtoToTransfer() {
        return dtoToTransfer;
    }

    public UUID getKey() {
        return key;
    }

    public void setRequestMethod(KafkaMethods requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setDtoToTransfer(List<CommentRequestTo> dtoToTransfer) {
        this.dtoToTransfer = dtoToTransfer;
    }

    public void setKey(UUID key) {
        this.key = key;
    }
}
