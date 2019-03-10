package com.example.demo.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonAutoConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.instance();
    }
}
