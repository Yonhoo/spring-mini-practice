package com.example.demo.factory.listener;

import org.springframework.core.ResolvableType;

public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> var);

    void removeApplicationListener(ApplicationListener<?> var);

    void multicastEvent(ApplicationEvent var);

    void multicastEvent(ApplicationEvent var, ResolvableType type);
}
