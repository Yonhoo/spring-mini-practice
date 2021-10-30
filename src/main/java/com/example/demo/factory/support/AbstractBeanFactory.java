package com.example.demo.factory.support;

import com.example.demo.exception.BeansException;
import com.example.demo.factory.FactoryBean;
import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.BeanPostProcessor;
import com.example.demo.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);

        if (bean != null) {
            getObjectForBeanInstance(bean, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object objectBean = createBean(name, beanDefinition);
        return getObjectForBeanInstance(objectBean, name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T) getBean(name));
    }

    /**
     * 如果是FactoryBean，则从FactoryBean#getObject中创建bean
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object object = beanInstance;
        if (beanInstance instanceof FactoryBean) {
            FactoryBean factoryBean = (FactoryBean) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    object = this.factoryBeanObjectCache.get(beanName);
                    if (object == null) {
                        object = factoryBean.getObject();
                        this.factoryBeanObjectCache.put(beanName, object);
                    }
                } else {
                    return factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception create " +
                        "factory object [ " + beanName + " ] ", e);
            }
        }
        return object;
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //有则覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

}
