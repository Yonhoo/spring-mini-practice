package com.example.demo.factory.listener;

import java.util.List;
import java.util.Properties;

public class DefaultEnvironment implements Environment {

    private List<Properties> properties;

    public void setProperties(List<Properties> properties){
        this.properties = properties;
    }

    public DefaultEnvironment() {}

    @Override
    public String getProperties(String key) {
        for (var property: this.properties) {
            String value = property.getProperty(key);
            if (!value.isEmpty()){
                return value;
            }
        }
        throw new IllegalArgumentException("no this properties!!!");
    }
}
