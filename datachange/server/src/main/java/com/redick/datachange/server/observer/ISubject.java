package com.redick.datachange.server.observer;

/**
 * 被观察者接口
 * @author liupenghui
 * @date 2022/2/15 11:32 上午
 */
public interface ISubject {

    /**
     * 注册观察者
     * @param iWatcher
     */
    void register(IWatcher iWatcher);

    /**
     * 移除观察者
     * @param iWatcher
     */
    void remove(IWatcher iWatcher);

    /**
     * 通知观察者
     * @param msg
     */
    void notify(String msg);
}
