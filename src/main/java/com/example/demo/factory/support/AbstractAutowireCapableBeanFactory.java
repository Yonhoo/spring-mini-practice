package com.example.demo.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.exception.BeansException;
import com.example.demo.factory.BeanFactoryAware;
import com.example.demo.factory.DisposableBean;
import com.example.demo.factory.InitializingBean;
import com.example.demo.factory.config.*;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 在每次创建bean的时候，注册有销毁方法的bean
        registerDisposableBeanIfNessary(beanName, bean, beanDefinition);
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 注册有销毁方法的bean，即bean继承自DisposableBean或有自定义的销毁方法
     * 为这个bean定义一个销毁的适配器，如果继承自DisposableBean,则默认执行
     * DisposableBean的destory方法，如果有自定义的destory方法，那么适配器也会执行
     * 自定义的销毁方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotBlank(
                beanDefinition.getDestoryMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 初始化时，感知是否有beanFactoryAware
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        //执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //TODO 执行bean的初始化方法
        // 如果bean有定义自己的初始化方法，需要执行一次
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable e) {
            throw new BeansException("invocation of init method of bean[" +
                    beanName + "] failed");
        }

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

                        if (value instanceof BeanReference) {
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
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws InvocationTargetException, IllegalAccessException {
        //TODO 后面会实现
        System.out.println("执行bean[" + beanName + "]的初始化方法");
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(
                    beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("could not find an init method name:" +
                        initMethodName);
            }
            initMethod.invoke(bean);
        }

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Object result = bean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
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
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }
}
