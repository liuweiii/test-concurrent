package org.liuwei.test.concurrent;

/**
 * Created by Administrator on 2017/1/28.
 */
public class App {
    public static void main(String[] args) {
        UnsafeSequence.testRun();
        SafeSequence.testRun();
    }
}
