package com.redick.datachange.server.controller;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2022/2/14 2:15 下午
 */
@RestController
public class DataChangeController {

    /**
     * guava 提供的多值 Map，一个 key 可以对应多个 value
     */
    private final Multimap<String, AsyncTask> dataIdContext = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("longPolling-timeout-checker-%d")
            .build();
    private final ScheduledExecutorService timeoutChecker = new ScheduledThreadPoolExecutor(1, threadFactory);

    private static boolean result = false;


    @Data
    private static class AsyncTask {
        // 长轮询请求的上下文，包含请求和响应体
        private AsyncContext asyncContext;
        // 超时标记
        private boolean timeout;

        public AsyncTask(AsyncContext asyncContext, boolean timeout) {
            this.asyncContext = asyncContext;
            this.timeout = timeout;
        }
    }

    @GetMapping("/listener")
    public void listener(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");
        // 异步
        AsyncContext asyncContext = request.startAsync(request, response);
        AsyncTask asyncTask = new AsyncTask(asyncContext, true);
        dataIdContext.put(key, asyncTask);
        timeoutChecker.schedule(() -> {
            if (asyncTask.timeout) {
                dataIdContext.remove(key, asyncTask);
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                asyncContext.complete();
            }
        }, 30000, TimeUnit.MILLISECONDS);
    }

    @PostMapping("/publishConfig")
    public String publishConfig(String key, String configData) {
        Collection<AsyncTask> asyncTasks = dataIdContext.removeAll(key);
        asyncTasks.forEach(e -> {
            e.setTimeout(false);
            HttpServletResponse response = (HttpServletResponse) e.getAsyncContext().getResponse();
            response.setStatus(HttpServletResponse.SC_OK);
            try {
                response.getWriter().println(configData);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.getAsyncContext().complete();
        });
        return "success";
    }

    @PostMapping("/async")
    public void async(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync(request, response);
        String name = request.getParameter("name");
        asyncContext.setTimeout(2000L);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                asyncContext.getResponse().getWriter().print(name + "：timeout");
                asyncContext.complete();
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });
        timeoutChecker.scheduleWithFixedDelay(() -> {
            try {
                if (result) {
                    asyncContext.getResponse().getWriter().print(name);
                    asyncContext.complete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 100L, TimeUnit.MILLISECONDS);
    }

    @PostMapping("/notify")
    public void notify(Boolean s) {
        result = s;
    }
}
