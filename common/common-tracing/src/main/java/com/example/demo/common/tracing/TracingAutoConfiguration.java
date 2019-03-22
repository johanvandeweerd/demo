package com.example.demo.common.tracing;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class TracingAutoConfiguration {

    @Bean
    public Tracer initTracer() {
        Configuration config = new Configuration("hello-world-service")
                .withSampler(SamplerConfiguration.fromEnv().withType("const").withParam(1))
                .withReporter(ReporterConfiguration.fromEnv().withLogSpans(true));
        GlobalTracer.register(config.getTracer());
        return config.getTracer();
    }
}
