package com.redick.datachange.server.publish;

/**
 * 发布者接口
 * @author liupenghui
 * @date 2022/2/14 5:02 下午
 */
public interface IPublish<T> {

    /**
     * 事件发布
     * @param msg msg
     */
    void publish(SubscribePublish subscribePublish, String topic, T msg);
}
