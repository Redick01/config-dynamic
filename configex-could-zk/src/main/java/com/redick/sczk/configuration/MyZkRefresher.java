package com.redick.sczk.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.zookeeper.config.ZookeeperPropertySourceLocator;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.*;

/**
 * @author liupenghui
 * @date 2022/3/18 17:17
 */
@Component
public class MyZkRefresher implements TreeCacheListener/*, ApplicationListener<ApplicationStartedEvent>*/, ApplicationRunner {

    @Resource
    private CuratorFramework curatorFramework;

    private TreeCache cache;

    private AtomicBoolean running = new AtomicBoolean(false);

    @Resource
    private ZookeeperPropertySourceLocator locator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (String path : locator.getContexts()) {
//
//        }
        cache = TreeCache.newBuilder(this.curatorFramework, "/configserver/dev/cloud-zk,dev").build();
        cache.start();
        cache.getListenable().addListener(this);
    }

//    @Override
//    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
//        try {
//            cache = TreeCache.newBuilder(this.curatorFramework, "/configserver/dev/cloud-zk,dev").build();
//            cache.start();
//            cache.getListenable().addListener(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        // 第一次刷新
        if (NODE_ADDED.equals(event.getType()) && this.running.compareAndSet(false, true)) {
            cache.getCurrentChildren("/configserver/dev/cloud-zk,dev").forEach((k, v) -> {
                System.out.println(k+ ":" + new String(v.getData(), Charset.forName("UTF-8")));
            });
            System.out.println(event.getType());
            System.out.println(111);
        }
        // 变动时候刷新
        if (NODE_UPDATED.equals(event.getType())) {
            cache.getCurrentChildren("/configserver/dev/cloud-zk,dev").forEach((k, v) -> {
                //System.out.println(k+ ":" + new String(v.getData(), Charset.forName("UTF-8")));
            });
        }
    }

    public String getEventDesc(TreeCacheEvent event) {
        StringBuilder out = new StringBuilder();
        byte[] data = event.getData().getData();
        if (data != null && data.length > 0) {
            out.append("data=").append(new String(data, Charset.forName("UTF-8")));
        }
        return out.toString();
    }
}
