package com.example.demo.worker.service;

import com.example.demo.api.HelloWorldMessage;
import datadog.trace.api.Trace;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WorkerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerListener.class);

    @Trace
    @KafkaListener(topics = "hello-world")
    public void listen(ConsumerRecord<String, HelloWorldMessage> consumerRecord) {
        LOGGER.info("Received message with headers {} and payload {}", consumerRecord.headers(), consumerRecord.value());
        sleep();
        LOGGER.info("Processed message with {} and payload {}", consumerRecord.headers(), consumerRecord.value());
    }

    private void sleep() {
        try {
            double interval = Math.random() * 100;
            Thread.sleep((long) interval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
