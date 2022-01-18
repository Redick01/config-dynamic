package com.redick.configex;

/**
 * @author liupenghui
 * @date 2022/1/18 11:03 上午
 */
public abstract class ConfigProfile {

    protected final String version;

    public ConfigProfile(String version) {
        super();
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
