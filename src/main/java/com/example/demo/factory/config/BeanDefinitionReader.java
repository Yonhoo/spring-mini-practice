package com.example.demo.factory.config;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.config.BeanDefinitionRegistry;
import com.example.demo.core.io.Resource;
import com.example.demo.core.io.ResourceLoader;


/*
*
* loadBeanDefinition就是创建BeanDefinition，
* 而BeanDefinition包含了创建class所需要的一切信息，
* reader就是将xml中关于class的内容读出来，创建BeanDefinition。
* */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
