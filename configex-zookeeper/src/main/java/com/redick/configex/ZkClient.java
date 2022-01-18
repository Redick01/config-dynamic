package com.redick.configex;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liupenghui
 * @date 2022/1/17 6:52 下午
 */
public class ZkClient {

    private static CuratorFramework client= null;

    public static void main(String[] args) throws Exception {
        client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        GetChildrenBuilder childrenBuilder = client.getChildren();
        List<String> list = childrenBuilder.watched().forPath("/configserver/userproject/1.0.0/config-group");
        final Map<String, String> configs = new HashMap<>(16);
        for (String child : list) {
            final Pair<String, String> keyValue = loadKey(ZKPaths.makePath("/configserver/userproject/1.0.0/config-group", child));
            if (keyValue != null) {
                System.out.println(keyValue.getKey() + ":" + keyValue.getValue());
                configs.put(keyValue.getKey(), keyValue.getValue());
            }
        }
    }

    private static Pair<String, String> loadKey(final String nodePath) throws Exception {

        final String nodeName = ZKPaths.getNodeFromPath(nodePath);
        final GetDataBuilder data = client.getData();
        final String value = new String(data.watched().forPath(nodePath), Charsets.UTF_8);
        return new ImmutablePair<>(nodeName, value);
    }
}
