package com.example.demo.worker.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Service
public class WorkerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerListener.class);

    private final Consumer<Long, String> consumer;

    public WorkerListener(Consumer<Long, String> consumer) {
        this.consumer = consumer;
    }

    @PostConstruct
    public void consume() {
        while (true) {
            LOGGER.debug("Polling kafka");
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            LOGGER.debug("Processing {} consumerRecords", consumerRecords.count());
            consumerRecords.forEach(this::process);
            consumer.commitAsync();
        }
    }

    private void process(ConsumerRecord<Long, String> consumerRecord) {
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
