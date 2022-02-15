package com.redick.datachange.server.observer;

/**
 * @author liupenghui
 * @date 2022/2/15 11:52 上午
 */
public class Main {

    public static void main(String[] args) {

        ISubject iSubject = new DefaultSubject();
        IWatcher iWatcher1 = new DefaultWatcher("观察者1");
        IWatcher iWatcher2 = new DefaultWatcher("观察者2");

        iSubject.register(iWatcher1);
        iSubject.register(iWatcher2);
        iSubject.notify("元宵节不加班");
    }
}
