package com.example.demo.context;

import com.example.demo.exception.BeansException;

public interface ConfigurableApplicationContext extends ApplicaitonContext{
    /*
    *  刷新容器
    *  @throws BeansException
    * */
    void refresh() throws BeansException;

    /*
    *
    *  关闭应用上下文
    * */
    void close();

    /*
     *
     *  向虚拟机注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     * */
    void registerShutdownHook();
}
