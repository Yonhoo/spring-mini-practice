package com.example.demo;

import com.example.demo.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
*
* singleton模式指的是对某个对象的完全共享，包括代码空间和数据空间，说白了，
* 如果一个类是singleton的，假如这个类有成员变量，那么这个成员变量的值是各个线程共享的
* （有点类似于static的样子了），当线程A往给变量赋了一个值以后，线程B就能读出这个值。
* 因此，对于前台Action，肯定不能使用singleton的模式，必须是一个线程请求对应一个独立的实例。
* 推而广之，只要是带数据成员变量的类，为了防止多个线程混用数据，就不能使用singleton。
* 对于我们用到的Service、Dao，之所以用了singleton，就是因为他们没有用到数据成员变量，
* 如果谁的Service需要数据成员变量，请设置singleton=false。
* 有状态的bean都使用Prototype作用域，而对无状态的bean则应该使用singleton作用域
*
*
*
* */

public class PrototypeTest {
    @Test
    void should_get_different_bean() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:prototype.xml");

        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        assertThat(car1 != car2).isTrue();
    }
}
