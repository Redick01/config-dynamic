package com.redick.datachange.server.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 * @author liupenghui
 * @date 2022/2/15 11:41 上午
 */
public class DefaultSubject implements ISubject {

    /**
     * 观察者列表
     */
    private final List<IWatcher> watcherList = new ArrayList<>(16);

    @Override
    public void register(IWatcher iWatcher) {
        watcherList.add(iWatcher);
    }

    @Override
    public void remove(IWatcher iWatcher) {
        watcherList.remove(iWatcher);
    }

    @Override
    public void notify(String msg) {
        watcherList.forEach(e -> e.notify(msg));
    }
}
