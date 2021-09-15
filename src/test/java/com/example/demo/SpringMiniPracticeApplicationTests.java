package com.example.demo;

import com.example.demo.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;
import com.example.demo.factory.config.BeanDefinition;

class SpringMiniPracticeApplicationTests {

    @Test
    void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }

}
