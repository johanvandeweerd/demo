package com.example.demo.common.tracing;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingAutoConfiguration {

    @Bean
    public Tracer tracer() {
        return GlobalTracer.get();
    }
}
