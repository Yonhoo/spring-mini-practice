package com.example.demo.factory;

import com.example.demo.exception.BeansException;

/*
*  感知所属的beanFactory
*
*
* */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
