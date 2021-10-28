package com.example.demo;

import com.example.demo.context.ApplicationContext;
import com.example.demo.context.ApplicationContextAware;
import com.example.demo.exception.BeansException;
import com.example.demo.factory.BeanFactory;
import com.example.demo.factory.BeanFactoryAware;

public class HelloService implements ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    public String sayHello() {
        System.out.println("hello world");
        return "hello world";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
