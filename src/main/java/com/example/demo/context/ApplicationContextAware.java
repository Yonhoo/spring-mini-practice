package com.example.demo.context;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.Aware;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicaitonContext applicaitonContext) throws BeansException;
}
