package com.redick.datachange.client;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author liupenghui
 * @date 2022/2/14 3:04 下午
 */
public class ConfigClient {

    private final CloseableHttpClient httpClient;

    private final RequestConfig requestConfig;

    public ConfigClient() {
        this.httpClient = HttpClientBuilder.create().build();
        this.requestConfig = RequestConfig.custom().setConnectTimeout(40000, TimeUnit.MILLISECONDS).build();
    }

    public void longPulling(String url, String key) throws IOException {
        String fullPath = url + "?key=" + key;
        HttpGet httpGet = new HttpGet(fullPath);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        switch (response.getCode()) {
            case 200:
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                response.close();
                String configInfo = sb.toString();
                System.out.println("changed config data : " + configInfo);
                longPulling(url, key);
                break;
            case 304:
                System.out.println("long pulling no data change");
                longPulling(url, key);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        ConfigClient client = new ConfigClient();
        client.longPulling("http://127.0.0.1:9099/server/v1/listener", "user");
    }
}
