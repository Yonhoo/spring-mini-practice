package com.example.demo.common;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.ConfigurableListableBeanFactory;
import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.BeanFactoryPostProcessor;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
        beanDefinition.getPropertyValues().addPropertyValue("name","ivy");
    }
}
