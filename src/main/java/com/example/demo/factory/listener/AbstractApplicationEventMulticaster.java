package com.example.demo.factory.listener;

import com.example.demo.factory.BeanFactory;
import com.example.demo.factory.config.ConfigurableBeanFactory;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {
    private final Set<ApplicationListener<?>> defaultApplicationListeners = new HashSet<>();

    @Nullable
    private ConfigurableBeanFactory beanFactory;

    public AbstractApplicationEventMulticaster() {
    }

    public AbstractApplicationEventMulticaster(BeanFactory factory) {
        if (!(beanFactory instanceof ConfigurableBeanFactory)) {
            throw new IllegalStateException("Not running in a ConfigurableBeanFactory: " + beanFactory);
        } else {
            this.beanFactory = (ConfigurableBeanFactory) beanFactory;
        }
    }

    private ConfigurableBeanFactory getBeanFactory() {
        if (this.beanFactory == null) {
            throw new IllegalStateException("ApplicationEventMulticaster cannot retrieve listener beans because it is not associated with a BeanFactory");
        } else {
            return this.beanFactory;
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        this.defaultApplicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> var) {
        this.defaultApplicationListeners.remove(var);
    }

    protected Collection<ApplicationListener<?>> getApplicationListeners() {
        return this.defaultApplicationListeners;
    }

    protected Collection<ApplicationListener<?>> getApplicationListeners(
            ApplicationEvent applicationEvent, ResolvableType eventType) {
        Set<ApplicationListener<?>> filteredListeners = new HashSet<>();

        Iterator varIter = this.defaultApplicationListeners.iterator();

        return this.defaultApplicationListeners.stream().filter(item->
            this.supportsEvent(item,eventType)
        ).collect(Collectors.toSet());
    }

    protected boolean supportsEvent(ApplicationListener<?> item, ResolvableType eventType){
        ResolvableType declaredType = ResolvableType.forClass(item.getClass());
        return declaredType == null || declaredType.getGeneric(0).isAssignableFrom(eventType);
    }

}
