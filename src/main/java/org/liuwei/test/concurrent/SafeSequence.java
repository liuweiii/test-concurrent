package org.liuwei.test.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/1/28.
 */
public class SafeSequence {
    private int value;

    public synchronized int getNext() {
        return value++;
    }

    private static class Test implements Runnable {
        private SafeSequence safeSequence;

        public Test(SafeSequence safeSequence) {
            this.safeSequence = safeSequence;
        }

        public void run() {
            set.add(safeSequence.getNext());
            latch.countDown();
        }
    }

    private static final Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
    private static final int THREAD_COUNT = 100000;
    private static final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        SafeSequence safeSequence = new SafeSequence();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new SafeSequence.Test(safeSequence)).start();
        }
        latch.await();

        System.out.println(set.size()+" = "+ THREAD_COUNT);
        if(set.size() < THREAD_COUNT){
            System.out.println("failed!");
        }
    }
}
