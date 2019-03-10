package com.example.demo.api;

import com.example.demo.common.core.ValueObject;

import java.beans.ConstructorProperties;

public class HelloWorldMessage extends ValueObject {

    private final String name;

    @ConstructorProperties({"name"})
    public HelloWorldMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
