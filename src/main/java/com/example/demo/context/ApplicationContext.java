package com.example.demo.context;

import com.example.demo.core.io.ResourceLoader;
import com.example.demo.factory.BeanFactory;
import com.example.demo.factory.ListableBeanFactory;

public interface ApplicationContext extends ListableBeanFactory, BeanFactory,
        ResourceLoader {

}
