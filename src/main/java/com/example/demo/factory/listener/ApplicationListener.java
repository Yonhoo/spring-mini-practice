package com.example.demo.factory.listener;

@FunctionalInterface
public interface ApplicationListener <E extends ApplicationEvent> {
    void onApplicationEvent(E var);
}
