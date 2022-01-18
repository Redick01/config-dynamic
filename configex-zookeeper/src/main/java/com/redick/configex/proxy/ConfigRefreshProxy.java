package com.redick.configex.proxy;

import com.redick.configex.GeneralConfigGroup;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liupenghui
 * @date 2022/1/18 2:44 下午
 */
@SuppressWarnings("all")
public class ConfigRefreshProxy<T> implements MethodInterceptor {

    private T target;

    private GeneralConfigGroup node;

    private Enhancer enhancer = new Enhancer();

    public ConfigRefreshProxy(T target, GeneralConfigGroup node) {
        this.target = target;
        this.node = node;
    }

    public T getProxy() {
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return method.invoke(target, objects);
    }
}
