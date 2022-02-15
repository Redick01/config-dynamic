package com.redick.datachange.server.publish.subscriber;

import com.redick.datachange.server.publish.SubscribePublish;

/**
 * @author liupenghui
 * @date 2022/2/14 5:07 下午
 */
public interface ISubscriber<T> {

    /**
     * 订阅
     * @param subscribePublish
     */
    void subscribe(SubscribePublish subscribePublish, String topic);

    /**
     * 取消订阅
     * @param subscribePublish
     */
    void unSubscribe(SubscribePublish subscribePublish, String topic);

    /**
     * @param publisher
     * @param m
     */
    void notify(String publisher, T m);
}
