package com.redick.configex;

import com.redick.configex.observer.ISubject;
import com.redick.configex.observer.IWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liupenghui
 * @date 2022/1/18 10:59 上午
 */
public abstract class GeneralConfigGroup extends ConcurrentHashMap<String, String> implements ConfigGroup, ISubject {

    private ConfigGroup configGroup;

    protected GeneralConfigGroup(ConfigGroup configGroup) {
        this.configGroup = configGroup;
    }

    @Override
    public String getValue(String key) {
        String value = super.get(key);
        if (null == value && null != configGroup) {
            value = configGroup.getValue(key);
        }
        return value;
    }

    @Override
    public String get(Object key) {
        return getValue(key.toString());
    }

    /**
     * set value and return old value
     * @param key config key
     * @param value config new value
     * @return old value
     */
    @Override
    public String put(String key, String value) {
        if (null != value) {
            value = value.trim();
        }
        String oldValue = super.get(key);
        // config value changed, notify
        if (null != value && !value.equals(oldValue)) {
            super.put(key, value);
            if (null != oldValue) {
                // notify zk
                notify(key, value);
            }
        }
        return oldValue;
    }

    /**
     * Watcher List
     */
    private final List<IWatcher> watchers = new ArrayList<>();

    @Override
    public void register(IWatcher watcher) {
        if (null == watcher) {
            throw new IllegalArgumentException("IWatcher is null !");
        }
        watchers.add(watcher);
    }

    @Override
    public void notify(String key, String value) {
        watchers.forEach(watcher -> {
            new Thread(() -> {
                watcher.notify(key, value);
            }).start();
        });
    }
}
