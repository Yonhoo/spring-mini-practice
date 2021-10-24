package com.example.demo;

import com.example.demo.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextTest {
    @Test
    public void testApplicationContext() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("ivy");

        Car car = applicationContext.getBean("car", Car.class);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
