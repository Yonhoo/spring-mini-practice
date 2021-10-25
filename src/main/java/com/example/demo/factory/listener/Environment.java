package com.example.demo.factory.listener;

import java.util.Properties;

public interface Environment {
    String getProperties(String name);
    void addProperties(Properties props);
}
