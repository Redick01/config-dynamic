package com.redick.configex;

import com.google.common.base.Charsets;
import com.redick.configex.reflect.ConfigReflectBean;
import com.redick.configex.zookeeper.ZookeeperConfigGroup;
import com.redick.configex.zookeeper.ZookeeperConfigProfile;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author liupenghui
 * @date 2022/1/19 10:36 上午
 */
public class GeneralConfConfigTest {

    @Test
    public void zkConfigTest() throws Exception {
        CountDownLatch count = new CountDownLatch(1);
        ZookeeperConfigProfile profile = new ZookeeperConfigProfile("1.0.0", "127.0.0.1:2181", "/configserver/userproject");
        GeneralConfigGroup group = new ZookeeperConfigGroup(profile, "config-group");
        ConfConfig confConfig = new ConfigReflectBean<ConfConfig>(ConfConfig.class, group).getConfig();
        GeneralConfigGroup group1 = new ZookeeperConfigGroup(profile, "test1");
        Test1 test1 = new ConfigReflectBean<Test1>(Test1.class, group1).getConfig();
        GeneralConfigGroup group2 = new ZookeeperConfigGroup(profile, "test2");
        Test2 test2 = new ConfigReflectBean<Test2>(Test2.class, group2).getConfig();
        System.out.println(confConfig.getMackey());


        //count.await();
    }

    @Test
    public void testzk() {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        final String nodePath = ZKPaths.makePath("/configserver/userproject/1.0.0", "dynamic-tp-zookeeper-demo");

        final GetChildrenBuilder childrenBuilder = client.getChildren();
        try {
            final List<String> children = childrenBuilder.watched().forPath(nodePath);
            StringBuilder content = new StringBuilder();
            for (String child : children) {
                String n = ZKPaths.makePath(nodePath, child);
                final String nodeName = ZKPaths.getNodeFromPath(n);
                final GetDataBuilder data = client.getData();
                final String value = new String(data.watched().forPath(n), Charsets.UTF_8);
                final Pair<String, String> keyValue = new ImmutablePair<>(nodeName, value);
                content.append(keyValue.getKey()).append("=").append(keyValue.getValue() + "\n");
                //configs.put(keyValue.getKey(), keyValue.getValue());
            }


            Properties properties = new Properties();
            properties.load(new StringReader(content.toString()));
            System.out.println(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
