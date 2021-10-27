package com.example.demo;

import com.example.demo.factory.DisposableBean;
import com.example.demo.factory.InitializingBean;

public class Student implements InitializingBean, DisposableBean {
    private String name;
    private Integer age;

    private Car car;


    public void customDestroyMethod(){
        System.out.println("i destroy in the method name: customDestroy");
    }

    public void customInitMethod(){
        this.name = "i born in the method name: customInit";
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("i destroy in the method name: destroy");
    }

    @Override
    public void afterPropertiesSet() {
        this.name = "i was born in the method name: afterPropertiesSet";
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student: { \n"+
                "name: "+name+"\n"
                + "age: " + age + "\n}";
    }
}
