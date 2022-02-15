package com.redick.datachange.server.publish;

/**
 * 默认消息发布实现
 * @author liupenghui
 * @date 2022/2/14 5:53 下午
 */
public class DefaultPublisher<T> implements IPublish<T> {

    private final String name;

    public DefaultPublisher(String name) {
        this.name = name;
    }

    @Override
    public void publish(SubscribePublish subscribePublish, String topic, T msg) {
        System.out.println(name + "发布消息到" + topic);
        subscribePublish.publish(this.name, topic, msg);
    }
}
