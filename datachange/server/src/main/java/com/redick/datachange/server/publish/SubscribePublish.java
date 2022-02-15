package com.redick.datachange.server.publish;

import com.redick.datachange.server.publish.subscriber.ISubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发布订阅中心
 * @author liupenghui
 * @date 2022/2/14 5:07 下午
 */
public class SubscribePublish<T> {

    /**
     * 根据主题订阅
     */
    private Map<String, List<ISubscriber>> map = new ConcurrentHashMap<>(1024);

    /**
     * 发布者发布事件
     * @param publisher
     * @param message
     */
    public void publish(String publisher, String topic, T message) {
        notify(publisher, topic, message);
    }

    /**
     * 订阅者订阅，加入订阅者列表
     */
    public void subscribe(ISubscriber subscriber, String topic) {
        map.computeIfAbsent(topic, key -> new ArrayList<>()).add(subscriber);
    }

    /**
     * 取消订阅
     */
    public void unSubscribe(ISubscriber subscriber, String topic) {
        map.get(topic).remove(subscriber);
    }


    public void notify(String publisher, String topic, T Msg) {
        List<ISubscriber> subscriberList = map.get(topic);
        for (ISubscriber subscriber : subscriberList) {
            subscriber.notify(publisher, Msg);
        }
    }
}
