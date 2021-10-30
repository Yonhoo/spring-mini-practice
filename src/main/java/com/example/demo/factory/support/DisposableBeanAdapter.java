package com.example.demo.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.exception.BeansException;
import com.example.demo.factory.DisposableBean;
import com.example.demo.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private final String destoryMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destoryMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            //如果继承了Disposablebean,则默认执行destroy
            ((DisposableBean) bean).destroy();
        }

        // 如果自定义的方法和Disposable的destory方法同名，则会被重写
        // 则上面的destory执行的就是自定义的方法，所以这里就要加判断
        // 避免同名的destory方法再次执行一次
        if (StrUtil.isNotBlank(destoryMethodName) && (!(bean instanceof DisposableBean
                && "destory".equals(destoryMethodName)))) {
            //执行自定义方法
            Method destoryMethod = ClassUtil.getPublicMethod(bean.getClass(), destoryMethodName);
            if (destoryMethod == null) {
                throw new BeansException("Couldn't find a destory method name " + destoryMethodName
                        + " on bean with name " + beanName);
            }
            destoryMethod.invoke(bean);
        }
    }
}
