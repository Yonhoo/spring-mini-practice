package com.example.demo.factory.config;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefintion加载完成后，但在bean实例化之前，
     * 提供修改BeanDefinition属性值的机制
     *
     * @param beanFactory
     * @throws org.springframework.beans.BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
