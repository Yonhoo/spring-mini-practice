package com.example.demo.factory.config;

import java.util.HashMap;
import java.util.Map;

public class PropertyValues {
    private final Map<String, Object> properties = new HashMap<>();

    public void addPropertyValue(String name, Object value) {
        properties.put(name, value);
    }

    public Object getPropertyValue(String name) {
        return properties.get(name);
    }
}
