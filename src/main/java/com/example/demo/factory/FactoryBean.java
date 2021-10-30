package com.example.demo.factory;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    boolean isSingleton();
}


