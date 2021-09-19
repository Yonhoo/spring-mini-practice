package com.example.demo;

import com.example.demo.factory.config.BeanReference;
import com.example.demo.factory.config.PropertyValues;
import com.example.demo.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;
import com.example.demo.factory.config.BeanDefinition;

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
        personPropertyValues.addPropertyValue("car",carBeanReference);
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, personPropertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");

        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
        assertThat(person.getCar().getBrand()).isEqualTo("mars");
    }
}
