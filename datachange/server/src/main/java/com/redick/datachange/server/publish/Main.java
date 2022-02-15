package com.redick.datachange.server.publish;

import com.redick.datachange.server.publish.subscriber.DefaultSubscriber;
import com.redick.datachange.server.publish.subscriber.ISubscriber;

/**
 * @author liupenghui
 * @date 2022/2/14 5:58 下午
 */
public class Main {

    public static void main(String[] args) {
        // 发布订阅器
        SubscribePublish<Msg<String>> subscribePublish = new SubscribePublish<>();
        // 订阅器
        ISubscriber<Msg<String>> iSubscriber1 = new DefaultSubscriber<>("订阅者1");
        ISubscriber<Msg<String>> iSubscriber2 = new DefaultSubscriber<>("订阅者2");
        iSubscriber1.subscribe(subscribePublish, "tpc_1");
        iSubscriber2.subscribe(subscribePublish, "tpc_2");
        // 事件发布
        IPublish<Msg<String>> iPublish1 = new DefaultPublisher<>("发布者1");
        IPublish<Msg<String>> iPublish2 = new DefaultPublisher<>("发布者2");
        // 事件
        Msg<String> msg1 = new Msg<>("发布者1", "发布消息1");
        iPublish1.publish(subscribePublish, "tpc_1", msg1);
        Msg<String> msg2 = new Msg<>("发布者2", "发布消息2");
        iPublish2.publish(subscribePublish, "tpc_2", msg2);

        iSubscriber1.unSubscribe(subscribePublish, "tpc_1");
        Msg<String> msg3 = new Msg<>("发布者1", "发布消息3");
        iPublish1.publish(subscribePublish, "tpc_1", msg3);
    }
}
