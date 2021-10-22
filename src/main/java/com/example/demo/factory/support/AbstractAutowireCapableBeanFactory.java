package com.example.demo.factory.support;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.config.*;
import cn.hutool.core.bean.BeanUtil;
import java.util.Arrays;


public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);

            //为bean填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

            //执行bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            initializeBean(beanName,bean,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed " + e.getMessage());
        }

        addSingleton(beanName, bean);
        return bean;
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        //执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean,beanName);

        //TODO 执行bean的初始化方法
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        //执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            Arrays.stream(bean.getClass().getDeclaredFields())
                    .forEach(item -> {

                        Object value = beanDefinition.getPropertyValues()
                                .getPropertyValue(item.getName());

                        if (value instanceof BeanReference){
                            value = getBean(((BeanReference) value).getBeanName());
                        }
                        BeanUtil.setFieldValue(bean, item.getName(), value);
                    });
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 执行bean的初始化方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws Throwable
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) {
        //TODO 后面会实现
        System.out.println("invokeInitMethods : 执行bean [" + beanName + "] 的初始化方法");
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Object result = bean;
        for (BeanPostProcessor postProcessor:getBeanPostProcessors()) {
            Object current = postProcessor.postProcessBeforeInitialization(result,beanName);
            if (current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName)
            throws BeansException {
        Object result = bean;
        for (BeanPostProcessor postProcessor:getBeanPostProcessors()) {
            Object current = postProcessor.postProcessAfterInitialization(result,beanName);
            if (current == null){
                return result;
            }
            result = current;
        }
        return result;
    }
}
