package com.redick.configex.zookeeper;

import com.redick.configex.ConfigProfile;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.utils.ZKPaths;

/**
 * base zk config profile
 * zookeeper server config
 * @author liupenghui
 * @date 2022/1/18 11:03 上午
 */
public class ZookeeperConfigProfile extends ConfigProfile {

    /**
     * zk connect address
     */
    private final String zkConnectStr;

    /**
     * config root node
     */
    private final String rootNode;

    /**
     * retry count
     */
    private final Integer retryCount;


    public ZookeeperConfigProfile(String version, String zkConnectStr, String rootNode) {
        super(version);
        this.zkConnectStr = zkConnectStr;
        this.rootNode = rootNode;
        this.retryCount = 3;
    }

    public String getZkConnectStr() {
        return zkConnectStr;
    }

    public String getRootNode() {
        return rootNode;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public String getRealConfigNode() {
        if (StringUtils.isBlank(this.version)) {
            throw new IllegalArgumentException("config version is null !");
        }
        return ZKPaths.makePath(rootNode, version);
    }

    @Override
    public String toString() {
        return "ZookeeperConfigProfile{" +
                "zkConnectStr='" + zkConnectStr + '\'' +
                ", rootNode='" + rootNode + '\'' +
                ", retryCount=" + retryCount +
                '}';
    }
}
