package com.example.demo.factory.listener;

import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.ConfigurableBeanFactory;
import com.example.demo.factory.config.PropertyValues;
import com.example.demo.factory.support.DefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.*;

@Slf4j
public class EnvironmentPostProcessorApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private final static String defaultPrefix = "application";
    private final static String defaultSuffix = ".properties";

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            this.onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        List<String> activeEnvironmentPath = event.getActiveEnvironmentPath();
        activeEnvironmentPath.forEach(path -> {
            try {
                Resource resource;
                if (path.isEmpty()) {
                    resource = new ClassPathResource(defaultPrefix + defaultSuffix);
                } else {
                    resource = new ClassPathResource(defaultPrefix + "-" + path + defaultSuffix);
                }
                registerEnvironmentBean(event, resource);
            } catch (Exception e) {
                log.warn("this environmentPath isn't exist {}", path);
            }
        });


    }

    private void registerEnvironmentBean(ApplicationEnvironmentPreparedEvent event, Resource resource) throws IOException {
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        ConfigurableBeanFactory beanFactory = event.getBeanFactory();
        ResolvableType resolvableType = ResolvableType.forInstance(beanFactory);
        ResolvableType parentType = ResolvableType.forClass(DefaultListableBeanFactory.class);
        if (resolvableType.isAssignableFrom(parentType)) {
            registerDefaultEnvironmentBeanDefinition(props, (DefaultListableBeanFactory) beanFactory);
        }
    }

    private void registerDefaultEnvironmentBeanDefinition(Properties props, DefaultListableBeanFactory beanFactory) {
        if (!beanFactory.containsBeanDefinition("environment")) {
            PropertyValues propertyValues = new PropertyValues();
            ArrayList<Properties> propList = new ArrayList<>();
            propList.add(props);
            propertyValues.addPropertyValue("properties", propList);
            BeanDefinition beanDefinition = new BeanDefinition(DefaultEnvironment.class, propertyValues);
            beanFactory.registerBeanDefinition("environment", beanDefinition);
        } else {
            BeanDefinition environmentBean = beanFactory
                    .getBeanDefinition("environment");
            ArrayList<Properties> properties = (ArrayList<Properties>) environmentBean
                    .getPropertyValues().getPropertyValue("properties");
            properties.add(props);
        }
        log.info("register environmentBeanDefinition success");
    }
}
