package com.example.demo;

import com.example.demo.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryBeanTest {
    @Test
    void should_get_object_from_factory_bean() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:factory-bean.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }
}
