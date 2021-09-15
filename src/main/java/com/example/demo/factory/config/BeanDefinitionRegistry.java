package com.example.demo.factory.config;

public interface BeanDefinitionRegistry {

    /*
     * register beanDefinition in Map
     * */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
