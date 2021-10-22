package com.example.demo.factory.listener;

import com.example.demo.factory.config.ConfigurableBeanFactory;

import java.util.List;

public class EventPublishingRunListener implements SpringApplicationRunListener {
    private final SimpleApplicationEventMulticaster initialMulticaster;

    public EventPublishingRunListener(List<ApplicationListener> listeners, ConfigurableBeanFactory beanFactory) {
        this.initialMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        listeners.forEach(this.initialMulticaster::addApplicationListener);
    }

    @Override
    public void starting(String context) {
        this.initialMulticaster.multicastEvent(new ApplicationStartingEvent(context));
    }

    @Override
    public void environmentPrepared(List<String> activeEnvironmentPath) {
        this.initialMulticaster.multicastEvent(
                new ApplicationEnvironmentPreparedEvent(activeEnvironmentPath,
                        this.initialMulticaster.getBeanFactory()));
    }
}
