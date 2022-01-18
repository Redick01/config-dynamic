package com.redick.configex;

import com.redick.configex.observer.IWatcher;
import com.redick.configex.proxy.ConfigRefreshProxy;

/**
 * @author liupenghui
 * @date 2022/1/18 2:42 下午
 */
public abstract class BaseConfigRefreshWatcher<T> implements IWatcher {

    private final T config;

    private final ConfigRefreshProxy<T> proxy;

    private final GeneralConfigGroup group;

    public BaseConfigRefreshWatcher(T config, ConfigRefreshProxy<T> proxy, GeneralConfigGroup group) {
        this.config = config;
        this.proxy = proxy;
        this.group = group;
        group.register(this);
        refresh();
    }

    public synchronized T getConfig() {
        return proxy.getProxy();
    }

    private synchronized void refresh() {
        doRefresh(config, group);
    }

    /**
     * init config
     * @param config config obj
     * @param group node
     */
    protected abstract void doRefresh(T config, GeneralConfigGroup group);

    @Override
    public void notify(String key, String value) {
        refresh();
    }
}
