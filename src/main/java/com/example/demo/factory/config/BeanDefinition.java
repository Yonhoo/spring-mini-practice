package com.example.demo.factory.config;

import java.util.Objects;

public class BeanDefinition {

    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destoryMethodName;

    public BeanDefinition(Class beanClass) {
        this(beanClass,null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = Objects.isNull(propertyValues) ?
                        new PropertyValues() : propertyValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestoryMethodName(String destoryMethodName) {
        this.destoryMethodName = destoryMethodName;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public String getDestoryMethodName() {
        return destoryMethodName;
    }
}


