package com.example.demo.factory.listener;

import java.util.List;

public class ApplicationEnvironmentPreparedEvent implements ApplicationEvent {
    List<String> activeEnvironmentPath;

    public ApplicationEnvironmentPreparedEvent(List<String> activeEnvironmentPath) {
        this.activeEnvironmentPath = activeEnvironmentPath;
    }
}
