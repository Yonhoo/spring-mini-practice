package com.example.demo;

import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.PropertyValues;
import com.example.demo.factory.listener.DefaultEnvironment;
import com.example.demo.factory.support.DefaultListableBeanFactory;
import com.example.demo.factory.xml.XmlBeanDefinitionReader;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentBeanTest {
    @Test
    void should_get_dev_properties() throws IOException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        PropertyValues propertyValues = new PropertyValues();
        Properties props = PropertiesLoaderUtils
                .loadProperties(new ClassPathResource("application-test.properties"));
        propertyValues.addPropertyValue("properties", List.of(props));
        BeanDefinition beanDefinition = new BeanDefinition(DefaultEnvironment.class, propertyValues);
        beanFactory.registerBeanDefinition("environment", beanDefinition);
        DefaultEnvironment environment = (DefaultEnvironment)beanFactory.getBean("environment");
        assertThat(environment.getProperties("person.name")).isEqualTo("DaYu");
    }
}
