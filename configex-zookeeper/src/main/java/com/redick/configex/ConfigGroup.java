package com.redick.configex;

import java.io.Closeable;
import java.util.Map;

/**
 * @author liupenghui
 * @date 2022/1/18 10:53 上午
 */
public interface ConfigGroup extends Map<String, String>, Closeable {

    /**
     * get config value
     * @param key config key
     * @return config value
     */
    String getValue(String key);
}