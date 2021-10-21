package com.example.demo.factory.listener;

import java.util.Properties;

public class DefaultEnviroment implements Enviroment{

    private final Properties properties;

    public DefaultEnviroment(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}
