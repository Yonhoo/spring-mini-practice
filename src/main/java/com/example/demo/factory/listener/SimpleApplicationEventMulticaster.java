package com.example.demo.factory.listener;

import com.example.demo.factory.BeanFactory;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.util.Iterator;
import java.util.concurrent.Executor;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    @Nullable
    private Executor taskExecutor;
    @Nullable
    private ErrorHandler errorHandler;

    public SimpleApplicationEventMulticaster() {
    }

    public SimpleApplicationEventMulticaster(BeanFactory factory) {
        super(factory);
    }

    public void setTaskExecutor(@Nullable Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Nullable
    protected Executor getTaskExecutor() {
        return taskExecutor;
    }

    @Nullable
    protected ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        this.multicastEvent(event, this.resolveDefaultEventType(event));
    }

    @Override
    public void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType) {
        ResolvableType type = eventType != null ? eventType : this.resolveDefaultEventType(event);
        Iterator iterator = this.getApplicationListeners(event,type).iterator();

        while (iterator.hasNext()){
            ApplicationListener listener = (ApplicationListener) iterator.next();
            if (taskExecutor !=null){
                taskExecutor.execute(()->{
                    this.invokeListener(listener,event);
                });
            }else {
                this.invokeListener(listener,event);
            }
        }
    }

    private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
        return ResolvableType.forInstance(event);
    }

    protected void invokeListener(ApplicationListener listener,ApplicationEvent event){
        if (this.errorHandler!=null){
            try {
                listener.onApplicationEvent(event);
            }catch (Throwable exception){
                errorHandler.handleError(exception);
            }
        }else {
            listener.onApplicationEvent(event);
        }
    }
}
