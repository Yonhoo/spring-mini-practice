package com.example.demo;

import com.example.demo.context.ClassPathXmlApplicationContext;
import com.example.demo.factory.listener.DefaultEnvironment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextTest {
    @Test
    public void testApplicationContext() throws Exception {
        List<String> envConfigPath= new ArrayList<>();
        envConfigPath.add("test");
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml", envConfigPath);

        Person person = applicationContext.getBean("person", Person.class);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("ivy");

        Car car = applicationContext.getBean("car", Car.class);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");

        DefaultEnvironment environment = (DefaultEnvironment) applicationContext.getBean("environment");
        assertThat(environment.getProperties("person.name")).isEqualTo("DaYu");
        assertThat(environment.getProperties("server.compression.enabled")).isEqualTo("false");
    }
}
