package com.example.demo.factory.support;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition == null){
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }
}
