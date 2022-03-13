package com.redick.datachange.server.scheduled;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liupenghui
 * @date 2022/3/8 10:12
 */
public class ScheduledTask {

    private static final AtomicInteger count = new AtomicInteger(0);

    private static final ScheduledThreadPoolExecutor SCHEDULED_TASK = new ScheduledThreadPoolExecutor(
            1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(Thread.currentThread().getThreadGroup(), r, "sc-task");
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        SCHEDULED_TASK.scheduleWithFixedDelay(() -> {
            System.out.println(111);
            if (count.get() == 1) {
                throw new IllegalArgumentException("my exception");
            }
            count.incrementAndGet();
        }, 0, 5, TimeUnit.SECONDS);
        latch.await();
    }
}
