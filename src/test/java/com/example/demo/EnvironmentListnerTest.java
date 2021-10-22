package com.example.demo;

import com.example.demo.factory.listener.DefaultEnvironment;
import com.example.demo.factory.support.DefaultListableBeanFactory;
import com.example.demo.factory.xml.XmlBeanDefinitionReader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentListnerTest {
    @Test
    void should_get_dev_properties(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        DefaultEnvironment environment = (DefaultEnvironment)beanFactory.getBean("environment");
        assertThat(environment.getProperties("person.name")).isEqualTo("DaYu");
    }
}
