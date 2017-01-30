package org.liuwei.test;


import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/1/30.
 */
public class TestScheduledExecutorService {
    @Test
    public void shouldSheduledSucceed() throws ExecutionException, InterruptedException {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> future = exec.schedule(new Callable<String>() {
            public String call() throws Exception {
                return "beep!";
            }
        },10, TimeUnit.SECONDS);
        System.out.println(System.nanoTime()+":begin");
        String result = future.get();
        System.out.println(System.nanoTime() + ":end "+result);
    }
}
