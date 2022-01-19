package com.redick.configex;

import com.redick.configex.reflect.ConfigReflectBean;
import com.redick.configex.zookeeper.ZookeeperConfigGroup;
import com.redick.configex.zookeeper.ZookeeperConfigProfile;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
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
        System.out.println(confConfig.getMackey());
        count.await();
    }
}
