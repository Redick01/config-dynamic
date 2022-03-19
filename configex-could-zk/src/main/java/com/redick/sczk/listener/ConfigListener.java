package com.redick.sczk.listener;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.cloud.zookeeper.config.ZookeeperPropertySourceLocator;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author liupenghui
 * @date 2022/3/18 14:02
 */
@Component
public class ConfigListener implements ApplicationListener<RefreshEvent> {

    @Autowired
    private ZookeeperPropertySourceLocator locator;

    private final String nodePath = "configserver/dev/cloud-zk,dev/";

    @Override
    @SuppressWarnings("serial")
    public void onApplicationEvent(RefreshEvent refreshEvent) {
        TreeCacheEvent event = (TreeCacheEvent) refreshEvent.getEvent();
        if (TreeCacheEvent.Type.NODE_UPDATED.equals(event.getType())) {
            ChildData childData = event.getData();
            String path = childData.getPath();
            List<String> list = locator.getContexts();
            String[] strings = path.split(nodePath);


            System.out.println("data=" + new String(childData.getData(), Charset.forName("UTF-8")));
        }
    }

    public String getEventDesc(TreeCacheEvent event) {
        StringBuilder out = new StringBuilder();
        out.append("type=").append(event.getType());
        out.append(", path=").append(event.getData().getPath());
        byte[] data = event.getData().getData();
        if (data != null && data.length > 0) {
            out.append(", data=").append(new String(data, Charset.forName("UTF-8")));
        }
        return out.toString();
    }

    public TreeCacheEvent getTreeCacheEvent(String s) {
        String[] strings = s.split(", ");
        for (String s1 : strings) {
            System.out.println(s1);
        }
        return null;
    }
}
