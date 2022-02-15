package com.redick.datachange.server.observer;

/**
 * 观察者接口
 * @author liupenghui
 * @date 2022/2/15 11:32 上午
 */
public interface IWatcher {

    /**
     * 观者者通知
     * @param msg
     */
    void notify(String msg);
}
