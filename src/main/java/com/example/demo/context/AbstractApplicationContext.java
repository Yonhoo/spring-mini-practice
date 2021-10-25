package com.example.demo.context;

import com.example.demo.core.io.DefaultResourceLoader;
import com.example.demo.exception.BeansException;
import com.example.demo.factory.ConfigurableListableBeanFactory;
import com.example.demo.factory.config.BeanFactoryPostProcessor;
import com.example.demo.factory.config.BeanPostProcessor;
import com.example.demo.factory.listener.EnvironmentPostProcessorApplicationListener;
import com.example.demo.factory.listener.EventPublishingRunListener;
import com.example.demo.factory.support.DefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException{
        //创建BeanFactory,并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //TODO 这里注册环境配置文件到beanFactory
        //TODO 主要是为了学习事件发布
        refreshEnvironment();

        //BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();
    }

    private void refreshEnvironment(){
        EventPublishingRunListener publishingRunListener =
                new EventPublishingRunListener(List.of(new EnvironmentPostProcessorApplicationListener()),
                        this.getBeanFactory());
        publishingRunListener.starting("initialMulticaster starting!!!\nloading environment !!!");
        publishingRunListener.environmentPrepared(getEnvironmentConfigPath());
    }

    protected abstract List<String> getEnvironmentConfigPath() ;

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        beanFactoryPostProcessorMap.values().
                forEach(item->item.postProcessBeanFactory(beanFactory));
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap =
                beanFactory.getBeansOfType(BeanPostProcessor.class);
        beanPostProcessorMap.values().forEach(beanFactory::addBeanPostProcessor);
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws org.springframework.beans.BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws org.springframework.beans.BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    public Object getBean(String name) throws org.springframework.beans.BeansException {
        return getBeanFactory().getBean(name);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();
}
