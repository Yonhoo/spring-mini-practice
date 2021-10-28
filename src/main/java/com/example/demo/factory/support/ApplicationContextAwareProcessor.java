package com.example.demo.factory.support;

import com.example.demo.context.ApplicationContext;
import com.example.demo.context.ApplicationContextAware;
import com.example.demo.exception.BeansException;
import com.example.demo.factory.config.BeanPostProcessor;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
       if (bean instanceof ApplicationContextAware){
           ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
       }
       return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
