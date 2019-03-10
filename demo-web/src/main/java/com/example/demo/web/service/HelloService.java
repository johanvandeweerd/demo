package com.example.demo.web.service;

import com.example.demo.api.HelloWorldMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.demo.common.json.ObjectMapperFactory.json;


@Service
public class HelloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);
    private static final String TOPIC = "hello-world";

    private final KafkaTemplate<String, String> template;

    public HelloService(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sayHello(String name) {
        HelloWorldMessage message = new HelloWorldMessage(name);
        LOGGER.info("Sending message to worker {}", message);
        template.send(TOPIC, json(message));
    }
}
