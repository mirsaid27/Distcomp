package org.example.discussionservice.kafkacl.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaRequestDto implements Serializable {

    private String requestId;
    private KafkaRequestType requestType;
    private String value;
}
