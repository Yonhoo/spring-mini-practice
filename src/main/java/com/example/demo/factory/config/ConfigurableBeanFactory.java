package com.example.demo.factory.config;

import com.example.demo.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory,SingletonBeanRegistry{
    /**
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /*
    *
    * 销毁单例bean
    *
    * */
    void destroySingletons();
}
