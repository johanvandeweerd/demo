package com.example.demo.worker.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
public class DemoWorkerConfiguration {

    @Bean
    public Consumer<Long, String> createConsumer(Properties consumerProperties) {
        Consumer<Long, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Collections.singletonList("hello-world"));
        return consumer;
    }

    @Bean
    public Properties consumerProperties() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(GROUP_ID_CONFIG, "hello-world-group");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }
}
