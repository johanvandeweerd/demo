package com.example.demo.common.tracing;

import io.opentracing.propagation.TextMap;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarrierTextMap implements TextMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarrierTextMap.class);

    private final Headers headers;

    public CarrierTextMap(Headers headers) {
        this.headers = headers;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        LOGGER.info("Getting iterator of {}", headers);
        List<Map.Entry<String, String>> entries = Stream.of(headers.toArray())
                .map(header -> (Map.Entry<String, String>) new AbstractMap.SimpleEntry(header.key(), new String(header.value())))
                .collect(Collectors.toList());
        return entries.iterator();
    }

    @Override
    public void put(String key, String value) {
        LOGGER.info("Adding header {} with value {}", key, value);
        headers.add(key, value.getBytes());
    }
}
