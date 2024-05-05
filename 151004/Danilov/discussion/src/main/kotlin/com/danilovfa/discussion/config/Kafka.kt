package com.danilovfa.discussion.config

import io.ktor.server.application.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import kotlin.concurrent.thread

private const val POLL_INTERVAL = 200L

fun Application.configureKafka() {
    val bootstrapServers = "localhost:9092"
    val topic = "TestTopic"

    val consumerProps = Properties()
    consumerProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
    consumerProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
    consumerProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
    consumerProps[ConsumerConfig.GROUP_ID_CONFIG] = "app-id"

    val consumer = KafkaConsumer<String, String>(consumerProps)
    consumer.subscribe(listOf("TestTopic"))

    thread {
        while (true) {
            consumer
                .poll(Duration.ofMillis(POLL_INTERVAL))
                .forEach { record ->
                    log.info("{topic=${record.topic()}, value=${record.value()}}")
                }
        }
    }
}