package com.redick.configex.observer;

/**
 * @author liupenghui
 * @date 2022/1/18 11:50 上午
 */
public interface ISubject {

    void register(IWatcher watcher);

    void notify(String key, String value);
}
