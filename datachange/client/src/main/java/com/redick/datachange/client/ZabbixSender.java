package com.redick.datachange.client;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import io.github.hengyunabc.metrics.ZabbixReporter;
import io.github.hengyunabc.zabbix.sender.DataObject;
import io.github.hengyunabc.zabbix.sender.SenderResult;

import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2022/3/12 13:44
 */
public class ZabbixSender {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args) throws Exception {
        test1("150.158.190.167", 10051);
    }

    public static void test1(String host, int port) throws Exception {
        io.github.hengyunabc.zabbix.sender.ZabbixSender zabbixSender = new io.github.hengyunabc.zabbix.sender.ZabbixSender(host, port, 10000, 10000);

        DataObject dataObject = new DataObject();
        dataObject.setHost("150.158.190.167");
        dataObject.setKey("a[test, jvm.mem.non-heap.used]");
        dataObject.setValue("10");
        dataObject.setClock(System.currentTimeMillis() / 1000);
        SenderResult result = zabbixSender.send(dataObject);

        System.out.println("result:" + result);
        if (result.success()) {
            System.out.println("send success.");
        } else {
            System.err.println("send fail!");
        }
    }

    public static void test2() throws Exception {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS).build();
        metrics.register("thread.pool.core.size", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return 5;
            }
        });
        reporter.start(5, TimeUnit.SECONDS);

        String hostName = "150.158.190.167";
        io.github.hengyunabc.zabbix.sender.ZabbixSender zabbixSender = new io.github.hengyunabc.zabbix.sender.ZabbixSender(hostName, 10051);
        ZabbixReporter zabbixReporter = ZabbixReporter.forRegistry(metrics).hostName(hostName).prefix("test.")
                .build(zabbixSender);

        zabbixReporter.start(1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(500);
    }
}
