package com.example.demo;

import cn.hutool.core.io.IoUtil;
import com.example.demo.core.io.DefaultResourceLoader;
import com.example.demo.core.io.FileSystemResource;
import com.example.demo.core.io.Resource;
import com.example.demo.factory.config.BeanReference;
import com.example.demo.factory.config.PropertyValues;
import com.example.demo.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;
import com.example.demo.factory.config.BeanDefinition;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class SpringMiniPracticeApplicationTests {

    @Test
    void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue("name", "derek");
        propertyValues.addPropertyValue("age", 18);
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");

        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
    }

    @Test
    void should_add_reference_bean_into_bean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        PropertyValues carPropertyValues = new PropertyValues();
        carPropertyValues.addPropertyValue("brand", "mars");
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, carPropertyValues);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);

        PropertyValues personPropertyValues = new PropertyValues();
        personPropertyValues.addPropertyValue("name", "derek");
        personPropertyValues.addPropertyValue("age", 18);
        BeanReference carBeanReference = new BeanReference("car");
        personPropertyValues.addPropertyValue("car", carBeanReference);
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, personPropertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");

        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
        assertThat(person.getCar().getBrand()).isEqualTo("mars");
    }

    @Test
    void testResourceLoader() throws Exception {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        try (InputStream inputStream = resource.getInputStream()) {
            String content = IoUtil.readUtf8(inputStream);
            assertThat(content).isEqualTo("hello world\r\n");
        }

        //加载文件系统资源
        resource = resourceLoader.getResource("src/test/resources/hello.txt");
        assertThat(resource instanceof FileSystemResource).isTrue();
        try (InputStream inputStream = resource.getInputStream()) {
            String content = IoUtil.readUtf8(inputStream);
            assertThat(content).isEqualTo("hello world\r\n");
        }
    }
}
