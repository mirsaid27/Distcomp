package com.example.publisherservice.kafkacl.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaResponseDto implements Serializable {

    private String responseId;
    private String value;
}
