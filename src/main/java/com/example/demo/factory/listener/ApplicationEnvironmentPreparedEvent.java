package com.example.demo.factory.listener;

import com.example.demo.factory.config.ConfigurableBeanFactory;

import java.util.List;

public class ApplicationEnvironmentPreparedEvent implements ApplicationEvent {
    private final List<String> activeEnvironmentPath;
    private final static String defaultPropertyPath = "";
    private final ConfigurableBeanFactory beanFactory;


    public ApplicationEnvironmentPreparedEvent(List<String> activeEnvironmentPath
    , ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.activeEnvironmentPath = activeEnvironmentPath;
    }

    public List<String> getActiveEnvironmentPath(){

        //TODO 添加path的时候是拼接的，这里的default就得单独弄了
        activeEnvironmentPath.add(defaultPropertyPath);
        return activeEnvironmentPath;
    }

    public ConfigurableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
