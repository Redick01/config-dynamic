package com.redick.consul.listener;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.consul.config.ConfigWatch;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author liupenghui
 * @date 2022/3/9 16:19
 */
@Component
public class ConfigChangeListener implements SmartApplicationListener {

    @Resource
    private ConsulClient client;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return ApplicationReadyEvent.class.isAssignableFrom(aClass) || RefreshEvent.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof RefreshEvent) {
            Response<List<GetValue>> response = client.getKVValues("config/configex-consul", "");
            List<GetValue> valueList = response.getValue();
            valueList.forEach(v -> {
                System.out.println(v.getKey() + ":" + v.getDecodedValue());
            });
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
