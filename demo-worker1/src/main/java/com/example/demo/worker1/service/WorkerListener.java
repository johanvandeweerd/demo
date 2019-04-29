package com.example.demo.worker1.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.demo.common.core.Constants.TOPIC_HELLO_WORLD;
import static com.example.demo.common.core.Constants.TOPIC_WORKER1_TO_WORKER2;

@Service
public class WorkerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerListener.class);

    private final KafkaTemplate<String, String> template;

    public WorkerListener(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @KafkaListener(topics = TOPIC_HELLO_WORLD)
    public void listen(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("Received message with headers {} and payload {}", consumerRecord.headers(), consumerRecord.value());
        template.send(TOPIC_WORKER1_TO_WORKER2, consumerRecord.value());
        LOGGER.info("Processed message with {} and payload {}", consumerRecord.headers(), consumerRecord.value());
    }
}
