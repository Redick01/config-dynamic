package com.redick.datachange.server.publish.subscriber;

import com.redick.datachange.server.publish.SubscribePublish;

/**
 * 订阅者
 * @author liupenghui
 * @date 2022/2/14 5:55 下午
 */
public class DefaultSubscriber<T> implements ISubscriber<T> {

    public String name;

    public DefaultSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void subscribe(SubscribePublish subscribePublish, String topic) {
        System.out.println(name + "订阅" + topic);
        subscribePublish.subscribe(this, topic);
    }

    @Override
    public void unSubscribe(SubscribePublish subscribePublish, String topic) {
        System.out.println(name + "取消订阅" + topic);
        subscribePublish.unSubscribe(this, topic);
    }

    @Override
    public void notify(String publisher, T m) {
        System.out.println(this.name + "消费" + publisher + "发布的消息:" + m.toString());
    }
}
