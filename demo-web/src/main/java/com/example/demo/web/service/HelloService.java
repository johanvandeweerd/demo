package com.example.demo.web.service;

import com.example.demo.api.HelloWorldMessage;
import com.example.demo.common.tracing.CarrierTextMap;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.demo.common.json.ObjectMapperFactory.json;
import static io.opentracing.propagation.Format.Builtin.TEXT_MAP;


@Service
public class HelloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);
    private static final String TOPIC = "hello-world";

    private final KafkaTemplate<String, String> template;
    private final Tracer tracer;

    public HelloService(KafkaTemplate<String, String> template, Tracer tracer) {
        this.template = template;
        this.tracer = tracer;
    }

    public void sayHello(String name) {
        try (Scope scope = tracer.buildSpan("sayHello").startActive(true)) {
            final Span span = scope.span();

            RecordHeaders headers = new RecordHeaders();
            tracer.inject(span.context(), TEXT_MAP, new CarrierTextMap(headers));
            HelloWorldMessage message = new HelloWorldMessage(name);
            ProducerRecord producerRecord = new ProducerRecord<>(TOPIC, null, null, null, json(message), headers);

            LOGGER.info("Sending message to worker {}", message);
            template.send(producerRecord);
        }
    }
}
