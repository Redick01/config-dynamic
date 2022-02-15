package com.redick.datachange.server.observer;

/**
 * @author liupenghui
 * @date 2022/2/15 11:50 上午
 */
public class DefaultWatcher implements IWatcher {

    private final String name;

    public DefaultWatcher(String name) {
        this.name = name;
    }

    @Override
    public void notify(String msg) {
        System.out.println("观察者" + name + "收到消息：" + msg);
    }
}
