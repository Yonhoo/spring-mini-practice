package com.example.demo.context;

import com.example.demo.exception.BeansException;

public interface ConfigurableApplicationContext extends ApplicaitonContext{
    /*
    *  刷新容器
    *  @throws BeansException
    * */
    void refresh() throws BeansException;
}
