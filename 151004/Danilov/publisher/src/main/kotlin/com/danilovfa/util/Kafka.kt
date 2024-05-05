package com.danilovfa.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

object Kafka {
    private var producer: KafkaProducer<String, String> = initProducer()

    private val mutex = Mutex()

    fun send(message: String) {
        val topic = "TestTopic"
        val record = ProducerRecord<String, String>(topic, message)

        producer.send(record)
    }

    private fun initProducer() : KafkaProducer<String, String> {
        val producerProps = Properties()
        producerProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        producerProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
        producerProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name

        return KafkaProducer<String, String>(producerProps)
    }
}