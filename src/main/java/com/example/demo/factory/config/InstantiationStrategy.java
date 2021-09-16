package com.example.demo.factory.config;

import com.example.demo.exception.BeansException;

/**
 * Bean的实例化策略
 *
 *  */
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
