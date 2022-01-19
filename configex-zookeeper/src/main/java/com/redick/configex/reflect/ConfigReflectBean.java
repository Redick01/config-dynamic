package com.redick.configex.reflect;

import com.redick.configex.BaseConfigRefreshWatcher;
import com.redick.configex.GeneralConfigGroup;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author liupenghui
 * @date 2022/1/19 3:00 下午
 */
public class ConfigReflectBean<T> extends BaseConfigRefreshWatcher<T> {

    private static final Map<Class<?>, Function<String, ?>> FIELD_TYPE_CONTAINER = new HashMap<>();

    static {
        FIELD_TYPE_CONTAINER.put(String.class,String::valueOf);
        FIELD_TYPE_CONTAINER.put(Integer.class,Integer::valueOf);
        FIELD_TYPE_CONTAINER.put(int.class,Integer::valueOf);
        FIELD_TYPE_CONTAINER.put(Boolean.class,Boolean::valueOf);
        FIELD_TYPE_CONTAINER.put(boolean.class,Boolean::valueOf);
        FIELD_TYPE_CONTAINER.put(Long.class,Long::valueOf);
        FIELD_TYPE_CONTAINER.put(long.class,Long::valueOf);
        FIELD_TYPE_CONTAINER.put(Double.class,Double::valueOf);
        FIELD_TYPE_CONTAINER.put(double.class,Double::valueOf);
        FIELD_TYPE_CONTAINER.put(Float.class,Float::valueOf);
        FIELD_TYPE_CONTAINER.put(float.class,Float::valueOf);
    }



    public ConfigReflectBean(Class<T> clazz, GeneralConfigGroup group) throws Exception {
        super(clazz.newInstance(), group);
    }

    @Override
    protected void doRefresh(T config, GeneralConfigGroup group) {
        Field[] fields = config.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(f -> {
            f.setAccessible(true);
            String key = f.getName();
            String value = group.getValue(key);
            if (null != value) {
                value = value.trim();
                try {
                    f.set(config, FIELD_TYPE_CONTAINER.get(f.getType()).apply(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
