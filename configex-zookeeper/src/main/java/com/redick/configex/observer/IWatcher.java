package com.redick.configex.observer;

/**
 * @author liupenghui
 * @date 2022/1/18 11:50 上午
 */
public interface IWatcher {

    /**
     * notify zk
     * @param key config key
     * @param value config value
     */
    void notify(String key, String value);
}
