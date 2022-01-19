package com.redick.configex.zookeeper;

import com.google.common.base.Charsets;
import com.redick.configex.ConfigGroup;
import com.redick.configex.GeneralConfigGroup;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author liupenghui
 * @date 2022/1/18 3:29 下午
 */
public class ZookeeperConfigGroup extends GeneralConfigGroup {

    private final ZookeeperConfigProfile profile;

    private final CuratorFramework client;

    private final String node;

    public ZookeeperConfigGroup(ZookeeperConfigProfile profile, String node) {
        this(null, profile, node);
    }

    public ZookeeperConfigGroup(ConfigGroup configGroup, ZookeeperConfigProfile profile, String node) {
        super(configGroup);
        this.profile = profile;
        this.node = node;
        this.client = CuratorFrameworkFactory.newClient(profile.getZkConnectStr(), new ExponentialBackoffRetry(1000, 3));
        this.client.start();
        // init listener
        this.client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                final WatchedEvent watchedEvent = event.getWatchedEvent();
                if (null != watchedEvent) {
                    switch (watchedEvent.getType()) {
                        case NodeChildrenChanged:
                            loadNode();
                            break;
                        case NodeDataChanged:
                            reloadKey(watchedEvent.getPath());
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        final CountDownLatch downLatch = new CountDownLatch(1);
        this.client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                if (newState == ConnectionState.CONNECTED) {
                    loadNode();
                    downLatch.countDown();
                } else if (newState == ConnectionState.RECONNECTED) {
                    loadNode();
                }
            }
        });
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void loadNode() {
        // get node path
        final String nodePath = ZKPaths.makePath(profile.getRealConfigNode(), node);

        final GetChildrenBuilder childrenBuilder = client.getChildren();
        try {
            final List<String> children = childrenBuilder.watched().forPath(nodePath);
            final Map<String, String> configs = new HashMap<>(16);
            for (String child : children) {
                final Pair<String, String> keyValue = loadKey(ZKPaths.makePath(nodePath, child));
                configs.put(keyValue.getKey(), keyValue.getValue());
                cleanAndPutAll(configs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanAndPutAll(Map<String, String> configs) {
        if (null != configs && configs.size() > 0) {
            if (this.size() > 0) {
                final Set<String> set = configs.keySet();
                this.keySet().stream().filter(e -> !set.contains(e)).forEach(super::remove);
            }
            configs.forEach(this::put);
        } else {
            super.clear();
        }
    }

    private void reloadKey(String path) {
        try {
            Pair<String, String> keyValue = loadKey(path);
            super.put(keyValue.getKey(), keyValue.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pair<String, String> loadKey(final String nodePath) throws Exception {
        final String nodeName = ZKPaths.getNodeFromPath(nodePath);
        final GetDataBuilder data = client.getData();
        final String value = new String(data.watched().forPath(nodePath), Charsets.UTF_8);
        return new ImmutablePair<>(nodeName, value);
    }

    @Override
    public String getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public void close() throws IOException {

    }
}
