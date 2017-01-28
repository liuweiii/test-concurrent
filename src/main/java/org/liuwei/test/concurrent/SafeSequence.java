package org.liuwei.test.concurrent;

import org.liuwei.test.common.Result;
import org.liuwei.test.exception.RuntimeInterruptedException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/1/28.
 */
public class SafeSequence {
    private int value;

    synchronized int getNext() {
        return value++;
    }

    private static class Test implements Runnable {
        private SafeSequence safeSequence;

        Test(SafeSequence safeSequence) {
            this.safeSequence = safeSequence;
        }

        public void run() {
            set.add(safeSequence.getNext());
            latch.countDown();
        }
    }

    private static final Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
    private static final int THREAD_COUNT = 10000;
    private static final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    public static void testRun(){
        SafeSequence safeSequence = new SafeSequence();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new SafeSequence.Test(safeSequence)).start();
        }
        try {
            latch.await();
        }catch (InterruptedException e){
            throw new RuntimeInterruptedException("test unsafe sequence failed! InterruptedException catch."+e.getMessage());
        }
        if(set.size() < THREAD_COUNT){
            Result.failed("synchronized i++ is safe operation, but test failed.maybe thread count is too small."+set.size()+" = "+THREAD_COUNT);
        }else{
            Result.succeed("synchronized i++ is safe operation."+set.size()+" = "+THREAD_COUNT);
        }
    }
}
