package com.example.demo.factory;

import com.example.demo.factory.config.AutowireCapableBeanFactory;
import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.BeansException;

public interface ConfigurableListableBeanFactory extends
ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException 如果找不到BeanDefintion
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     *
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;
}
