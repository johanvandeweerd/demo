package com.example.demo.worker.service;

import com.example.demo.api.HelloWorldMessage;
import com.example.demo.common.tracing.CarrierTextMap;
import io.opentracing.Scope;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static io.opentracing.propagation.Format.Builtin.TEXT_MAP;

@Service
public class WorkerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerListener.class);

    private final Tracer tracer;

    public WorkerListener(Tracer tracer) {
        this.tracer = tracer;
    }

    @KafkaListener(topics = "hello-world")
    public void listen(ConsumerRecord<String, HelloWorldMessage> consumerRecord) {
        SpanContext extractedContext = GlobalTracer.get().extract(TEXT_MAP, new CarrierTextMap(consumerRecord.headers()));
        try (Scope scope = tracer.buildSpan("listen").asChildOf(extractedContext).startActive(true)) {
            LOGGER.info("Received message with headers {} and payload {}", consumerRecord.headers(), consumerRecord.value());
            sleep();
            LOGGER.info("Processed message with {} and payload {}", consumerRecord.headers(), consumerRecord.value());
        }
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
