package org.liuwei.test.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/1/28.
 */
public class UnsafeSequence {
    private int value;

    public int getNext() {
        return value++;
    }

    private static class Test implements Runnable {
        private UnsafeSequence unsafeSequence;

        public Test(UnsafeSequence unsafeSequence) {
            this.unsafeSequence = unsafeSequence;
        }

        public void run() {
            set.add(unsafeSequence.getNext());
            latch.countDown();
        }
    }

    private static final Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
    private static final int THREAD_COUNT = 100000;
    private static final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new UnsafeSequence.Test(unsafeSequence)).start();
        }
        latch.await();

        System.out.println(set.size()+" = "+ THREAD_COUNT);
        if(set.size() < THREAD_COUNT){
            System.out.println("failed!");
        }
    }
}
