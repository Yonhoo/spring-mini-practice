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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

@Slf4j
public class EnvironmentPostProcessorApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>{
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent){
            this.onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event){
        List<String> activeEnvironmentPath = event.getActiveEnvironmentPath();
        activeEnvironmentPath.forEach(path->{
            try {
                Resource resource = new ClassPathResource(path);
                Properties props = PropertiesLoaderUtils.loadProperties(resource);
                ConfigurableBeanFactory beanFactory = event.getBeanFactory();
                ResolvableType resolvableType = ResolvableType.forInstance(beanFactory);
                ResolvableType parentType = ResolvableType.forClass(DefaultListableBeanFactory.class);
                if (resolvableType.isAssignableFrom(parentType)){
                    registerDefaultEnviromenBeanDefinition(props, (DefaultListableBeanFactory) beanFactory);
                }
            }catch (Exception e){
                log.warn("this environmentPath isn't exist {}",path);
            }
        });


    }

    private void registerDefaultEnviromenBeanDefinition(Properties props, DefaultListableBeanFactory beanFactory) {
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue("properties", props);
        BeanDefinition beanDefinition = new BeanDefinition(DefaultEnviroment.class,propertyValues);
        beanFactory.registerBeanDefinition("enviroment",beanDefinition);
    }
}
