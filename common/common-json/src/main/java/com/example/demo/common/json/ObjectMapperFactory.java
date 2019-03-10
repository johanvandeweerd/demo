package com.example.demo.common.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.annotation.PropertyAccessor.*;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class ObjectMapperFactory {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(FIELD, Visibility.ANY)
            .setVisibility(CREATOR, Visibility.NONE)
            .setVisibility(GETTER, Visibility.NONE)
            .setVisibility(IS_GETTER, Visibility.NONE)
            .setVisibility(SETTER, Visibility.NONE);

    public static ObjectMapper instance() {
        return OBJECT_MAPPER;
    }

    public static String json(Object object) {
        try {
            return instance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
