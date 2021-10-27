package com.example.demo;

import com.example.demo.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InitAndDestroyMethodTest {
    @Test
    void should_run_init_and_destroy_default_method() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                        "classpath:init-and-destroy-default-method.xml");

        Student student = applicationContext.getBean("student", Student.class);
        // 在default-init方法中执行name的赋值
        assertThat(student.getName()).isEqualTo("i was born in the method name: afterPropertiesSet");
    }

    @Test
    void should_run_init_and_destroy_custom_method() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(
                        "classpath:init-and-destroy-custom-method.xml");

        Student student = applicationContext.getBean("student", Student.class);
        // 在custom-init方法中执行name的赋值
        assertThat(student.getName()).isEqualTo("i born in the method name: customInit");
    }
}
