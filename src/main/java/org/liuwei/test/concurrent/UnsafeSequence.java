package org.liuwei.test.concurrent;

import org.liuwei.test.exception.RuntimeInterruptedException;
import org.liuwei.test.common.Result;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/1/28.
 */
class UnsafeSequence {
    private int value;

    int getNext() {
        return value++;
    }

    private static class Test implements Runnable {
        private UnsafeSequence unsafeSequence;

        Test(UnsafeSequence unsafeSequence) {
            this.unsafeSequence = unsafeSequence;
        }

        public void run() {
            set.add(unsafeSequence.getNext());
            latch.countDown();
        }
    }

    private static final Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
    private static final int THREAD_COUNT = 10000;
    private static final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    static void testRun(){
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new UnsafeSequence.Test(unsafeSequence)).start();
        }
        try {
            latch.await();
        }catch(InterruptedException e){
            throw new RuntimeInterruptedException("test unsafe sequence failed! InterruptedException catch."+e.getMessage());
        }
        if(set.size() < THREAD_COUNT){
            Result.succeed("i++ is unsafe operation."+set.size()+" != "+THREAD_COUNT);
        }else{
            Result.failed("i++ is unsafe operation, but test failed."+set.size()+" = "+THREAD_COUNT);
        }
    }
}
